package br.eti.rslemos.brill;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.HaltingStrategy.HaltingStrategyContext;
import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;
import br.eti.rslemos.brill.RuleSelectStrategy.RuleSelectStrategyContext;
import br.eti.rslemos.brill.rules.RuleCreationException;
import br.eti.rslemos.brill.rules.RuleFactory;

public class RulesetTrainer {

	private final Tagger baseTagger;
	private final List<RuleFactory> ruleFactories;

	private final HaltingStrategy haltingStrategy;
	private final RuleSelectStrategy ruleSelectStrategy;

	public RulesetTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories) {
		this(baseTagger, ruleFactories, new ThresholdHaltingStrategy(1),
				new ScoringRuleSelectStrategy(new BrillScoringStrategy()));
	}

	public RulesetTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories,
			HaltingStrategy haltingStrategy, RuleSelectStrategy ruleSelectStrategy) {
		this.baseTagger = baseTagger;
		this.ruleFactories = ruleFactories;
		this.haltingStrategy = haltingStrategy;
		this.ruleSelectStrategy = ruleSelectStrategy;
	}

	public RuleBasedTagger train(List<List<Token>> proofCorpus) {
		TrainingContext trainingContext = new TrainingContext(proofCorpus);

		trainingContext.applyBaseTagger();
		List<Rule> rules = trainingContext.discoverRules();

		return new RuleBasedTagger(baseTagger, rules);
	}

	private class TrainingContext {

		private final List<List<Token>> proofCorpus;
		private BufferingContext[] trainingCorpus;

		public TrainingContext(List<List<Token>> proofCorpus) {
			this.proofCorpus = proofCorpus;
		}

		public void applyBaseTagger() {
			trainingCorpus = new BufferingContext[proofCorpus.size()];

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

		public List<Rule> discoverRules() {
			LinkedList<Rule> rules = new LinkedList<Rule>();

			HaltingStrategyContext haltingContext = haltingStrategy.getContext(
					proofCorpus, trainingCorpus);

			RuleSelectStrategyContext ruleSelectStrategyContext = ruleSelectStrategy.getContext(proofCorpus, trainingCorpus);
			
			boolean shouldTryMore;
			do {
				shouldTryMore = false;
				Rule bestRule = ruleSelectStrategyContext.selectBestRule(produceAllPossibleRules());

				if (bestRule != null) {
					applyRule(bestRule);

					if (shouldTryMore = haltingContext
							.updateAndTest(trainingCorpus))
						rules.add(bestRule);
				}

			} while (shouldTryMore);

			return rules;
		}

		private void applyRule(Rule bestRule) {
			for (BufferingContext trainingSentence : trainingCorpus)
				RuleBasedTagger.applyRule(trainingSentence, bestRule);
		}

		private Set<Rule> produceAllPossibleRules() {
			Set<Rule> allPossibleRules = new HashSet<Rule>();

			int i = 0;
			for (List<Token> proofSentence : proofCorpus) {
				BufferingContext trainingSentence = trainingCorpus[i++];

				try {
					for (Token proofToken : proofSentence) {
						Token trainingToken = trainingSentence.next();

						if (!ObjectUtils.equals(proofToken.getTag(),
								trainingToken.getTag())) {
							Collection<Rule> localPossibleRules = produceAllPossibleRules(
									trainingSentence, proofToken);
							allPossibleRules.addAll(localPossibleRules);
						}
					}
				} finally {
					trainingSentence.reset();
				}
			}

			return allPossibleRules;
		}

		private Collection<Rule> produceAllPossibleRules(Context context,
				Token target) {
			try {
				Rule[] rules = new Rule[ruleFactories.size()];

				int i = 0;
				for (RuleFactory factory : ruleFactories)
					rules[i++] = factory.create(context, target);

				return Arrays.asList(rules);
			} catch (RuleCreationException e) {
				throw new RuntimeException("Error creating rule", e);
			}
		}

	}
}
