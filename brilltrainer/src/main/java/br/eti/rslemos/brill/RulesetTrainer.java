package br.eti.rslemos.brill;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

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
		Rule selectBestRule(Queue<Score> rules);
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

				Token[] baseTaggedSentence = new DefaultToken[proofSentence.size()];
				for (int j = 0; j < baseTaggedSentence.length; j++) {
					baseTaggedSentence[j] = new DefaultToken(proofSentence.get(j).getWord());
				}

				baseTagger.tagSentence(Arrays.asList(baseTaggedSentence));
				trainingCorpus[i] = RuleBasedTagger.prepareContext(baseTaggedSentence);
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
			ScoreBoard board = new ScoreBoard();
			do {
				shouldTryMore = false;
				board.newRound();
				
				produceAllPossibleRules(board);
				
				Rule bestRule = ruleSelectStrategy.selectBestRule(board.getRulesByPriority());

				if (bestRule != null) {
					applyRule(bestRule);

					if (shouldTryMore = haltingStrategy.updateAndTest())
						rules.add(bestRule);
				}
			} while (shouldTryMore);

			return rules;
		}

		private void applyRule(Rule bestRule) {
			for (BufferingContext trainingSentence : trainingCorpus)
				RuleBasedTagger.applyRule(trainingSentence, bestRule);
		}

		private void produceAllPossibleRules(ScoreBoard board) {
			int i = 0;
			for (List<Token> proofSentence : proofCorpus) {
				BufferingContext trainingSentence = trainingCorpus[i++];

				try {
					for (Token proofToken : proofSentence) {
						produceAllPossibleRules(board, proofToken, trainingSentence);
					}
				} finally {
					trainingSentence.reset();
				}
			}
		}

		private void produceAllPossibleRules(ScoreBoard board, Token proofToken, BufferingContext trainingSentence) {
			Token trainingToken = trainingSentence.next();

			if (!ObjectUtils.equals(proofToken.getTag(), trainingToken.getTag())) {
				Collection<Rule> localPossibleRules = ruleFactoryStrategy.produceAllPossibleRules(trainingSentence, proofToken);
				
				for (Rule localPossibleRule : localPossibleRules) {
					board.addTruePositive(localPossibleRule);
				}
			}
		}
	}
	
	public static class Score implements Comparable<Score> {
		public final Rule rule;
		private int score;
		
		protected Score(Rule rule) {
			this.rule = rule;
			score = 0;
		}
		
		public void inc() {
			score++;
		}
		
		public void dec() {
			score--;
		}
		
		public int getScore() {
			return score;
		}

		public int compareTo(Score o) {
			return o.score - score;
		}
	}

	public static class ScoreBoard {
		private final Map<Rule, Score> rules = new HashMap<Rule, Score>();
		
		public void addTruePositive(Rule rule) {
			Score score = rules.get(rule);
			
			if (score == null) {
				score = new Score(rule);
				rules.put(rule, score);
			}
			
			score.inc();
		}

		public void newRound() {
			rules.clear();
		}

		public Queue<Score> getRulesByPriority() {
			return new PriorityQueue<Score>(rules.values());
		}		
	}
}
