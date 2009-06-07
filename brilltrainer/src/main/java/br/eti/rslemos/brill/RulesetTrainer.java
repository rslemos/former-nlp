package br.eti.rslemos.brill;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Map.Entry;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;
import br.eti.rslemos.brill.rules.RuleFactory;

public class RulesetTrainer {
	public static interface HaltingStrategy {
		void setTrainingContext(TrainingContext trainingContext);
		boolean updateAndTest();
	}
	
	public static interface RuleSelectStrategy {
		void setTrainingContext(TrainingContext trainingContext);
		Rule selectBestRule(Queue<Map.Entry<Rule, Integer>> rules);
	}
	
	public static interface RuleProducingStrategy {
		Collection<Rule> produceAllPossibleRules(Context context, Token target);
	}
	
	private final Tagger baseTagger;

	private final HaltingStrategy haltingStrategy;
	private final RuleSelectStrategy ruleSelectStrategy;
	private final RuleProducingStrategy ruleFactoryStrategy;

	public RulesetTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories) {
		this(baseTagger, new RuleFactoryStrategy(ruleFactories), new ThresholdHaltingStrategy(1),
				new ScoringRuleSelectStrategy(new BrillScoringStrategy()));
	}

	public RulesetTrainer(Tagger baseTagger, RuleProducingStrategy ruleFactoryStrategy,
			HaltingStrategy haltingStrategy, RuleSelectStrategy ruleSelectStrategy) {
		this.baseTagger = baseTagger;
		this.ruleFactoryStrategy = ruleFactoryStrategy;
		this.haltingStrategy = haltingStrategy;
		this.ruleSelectStrategy = ruleSelectStrategy;
	}

	
	public Tagger getBaseTagger() {
		return baseTagger;
	}

	public HaltingStrategy getHaltingStrategy() {
		return haltingStrategy;
	}

	public RuleSelectStrategy getRuleSelectStrategy() {
		return ruleSelectStrategy;
	}

	public RuleProducingStrategy getRuleFactoryStrategy() {
		return ruleFactoryStrategy;
	}

	public synchronized RuleBasedTagger train(List<List<Token>> proofCorpus) {
		TrainingContext trainingContext = new TrainingContext(proofCorpus);
		
		trainingContext.applyBaseTagger();
		List<Rule> rules = trainingContext.discoverRules();

		return new RuleBasedTagger(baseTagger, rules);
	}

	public class TrainingContext {

		public final List<List<Token>> proofCorpus;
		public final BufferingContext[] trainingCorpus;

		public TrainingContext(List<List<Token>> proofCorpus) {
			this.proofCorpus = proofCorpus;
			this.trainingCorpus = new BufferingContext[proofCorpus.size()];
		}

		private void applyBaseTagger() {

			for (int i = 0; i < trainingCorpus.length; i++) {
				List<Token> proofSentence = proofCorpus.get(i);

				Token[] baseTaggedSentence = new DefaultToken[proofSentence
						.size()];
				for (int j = 0; j < baseTaggedSentence.length; j++) {
					baseTaggedSentence[j] = new DefaultToken(proofSentence.get(
							j).getWord());
				}

				baseTagger.tagSentence(Arrays.asList(baseTaggedSentence));
				trainingCorpus[i] = RuleBasedTagger
						.prepareContext(baseTaggedSentence);
			}
		}

		private List<Rule> discoverRules() {
			try {
				ruleSelectStrategy.setTrainingContext(this);
				haltingStrategy.setTrainingContext(this);
		
				return discoverRules0();
			} finally {
				ruleSelectStrategy.setTrainingContext(null);
				haltingStrategy.setTrainingContext(null);			
			}
		}

		private List<Rule> discoverRules0() {
			LinkedList<Rule> rules = new LinkedList<Rule>();

			boolean shouldTryMore;
			do {
				shouldTryMore = false;
				final Map<Rule, Integer> allRules = produceAllPossibleRules();
				
				Comparator<Entry<Rule, Integer>> comparator = new Comparator<Entry<Rule,Integer>>() {

					public int compare(Entry<Rule, Integer> o1, Entry<Rule, Integer> o2) {
						return o2.getValue() - o1.getValue();
					}
					
				};
				
				if (!allRules.isEmpty()) {
					PriorityQueue<Entry<Rule, Integer>> priorules = new PriorityQueue<Map.Entry<Rule, Integer>>(allRules.size(), comparator);
					priorules.addAll(allRules.entrySet());
					
					Rule bestRule = ruleSelectStrategy.selectBestRule(priorules);
	
					if (bestRule != null) {
						applyRule(bestRule);
	
						if (shouldTryMore = haltingStrategy.updateAndTest())
							rules.add(bestRule);
					}
				}
			} while (shouldTryMore);

			return rules;
		}

		private void applyRule(Rule bestRule) {
			for (BufferingContext trainingSentence : trainingCorpus)
				RuleBasedTagger.applyRule(trainingSentence, bestRule);
		}

		private Map<Rule, Integer> produceAllPossibleRules() {
			Map<Rule, Integer> allPossibleRules = new HashMap<Rule, Integer>();

			int i = 0;
			for (List<Token> proofSentence : proofCorpus) {
				BufferingContext trainingSentence = trainingCorpus[i++];

				try {
					for (Token proofToken : proofSentence) {
						Token trainingToken = trainingSentence.next();

						if (!ObjectUtils.equals(proofToken.getTag(),
								trainingToken.getTag())) {
							Collection<Rule> localPossibleRules = new LinkedHashSet<Rule>(ruleFactoryStrategy.produceAllPossibleRules(trainingSentence, proofToken));
							for (Rule localPossibleRule : localPossibleRules) {
								Integer count = allPossibleRules.get(localPossibleRule);
								
								if (count == null)
									count = 0;
								
								count += 1;
								
								allPossibleRules.put(localPossibleRule, count);
							}
						}
					}
				} finally {
					trainingSentence.reset();
				}
			}

			return allPossibleRules;
		}
	}
}
