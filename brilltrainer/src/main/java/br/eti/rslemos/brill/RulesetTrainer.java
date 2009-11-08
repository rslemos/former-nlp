package br.eti.rslemos.brill;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.rules.RuleCreationException;
import br.eti.rslemos.brill.rules.RuleFactory;

public class RulesetTrainer {
	private final Tagger baseTagger;

	private final List<RuleFactory> ruleFactories;
	private final int threshold;

	public RulesetTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories) {
		this(baseTagger, ruleFactories, 1);
	}

	public RulesetTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories, int threshold) {
		this.baseTagger = baseTagger;
		this.ruleFactories = ruleFactories;
		this.threshold = threshold;
	}

	private TrainingContext createTrainingContext(List<Sentence> proofCorpus) {
		return new TrainingContext(proofCorpus);
	}

	public synchronized RuleBasedTagger train(List<Sentence> proofCorpus) {
		TrainingContext trainingContext = createTrainingContext(proofCorpus);
		
		trainingContext.applyBaseTagger();
		List<Rule> rules = trainingContext.discoverRules();

		return new RuleBasedTagger(baseTagger, rules);
	}

	public class TrainingContext {

		public final List<Sentence> proofCorpus;
		public final DelayedContext[] trainingCorpus;
		
		public TrainingContext(List<Sentence> proofCorpus) {
			this.proofCorpus = proofCorpus;
			this.trainingCorpus = new DelayedContext[proofCorpus.size()];
		}

		private void applyBaseTagger() {

			for (int i = 0; i < trainingCorpus.length; i++) {
				Sentence proofSentence = proofCorpus.get(i);

				Token[] baseTaggedSentence = new DefaultToken[proofSentence.size()];
				for (int j = 0; j < baseTaggedSentence.length; j++) {
					baseTaggedSentence[j] = new DefaultToken(proofSentence.get(j).getWord());
				}

				final DefaultSentence baseTaggedSentence0 = new DefaultSentence(baseTaggedSentence);
				baseTagger.tag(baseTaggedSentence0);
				trainingCorpus[i] = RuleBasedTagger.prepareContext(baseTaggedSentence0);
			}
		}

		public List<Rule> discoverRules() {
			LinkedList<Rule> rules = new LinkedList<Rule>();

			ScoreBoard board = new ScoreBoard();
			do {
				board.newRound();
				
				produceAllPossibleRules(board);
				
				Score bestScore = selectBestRule(board.getRulesByPriority());
				Rule bestRule = bestScore.rule;
				
				if (bestRule != null) {
					applyRule(bestRule);

					if (bestScore.getScore() >= threshold) {
						rules.add(bestRule);
						for (Iterator<Rule> iterator = board.iterator(); iterator.hasNext();) {
							Rule rule = iterator.next();
							if (rule == bestRule)
								iterator.remove();
							else {
								if (rule.firingDependsOnTag(bestRule.getFrom()) || rule.firingDependsOnTag(bestRule.getTo()))
									iterator.remove();
							}
						}
						
						continue;
					} 
				} 
				
				break;
			} while (true);

			return rules;
		}

		private void applyRule(Rule bestRule) {
			for (DelayedContext trainingSentence : trainingCorpus)
				RuleBasedTagger.applyRule(trainingSentence, bestRule);
		}

		private void produceAllPossibleRules(ScoreBoard board) {
			int i = 0;
			for (Sentence proofSentence : proofCorpus) {
				DelayedContext trainingSentence = trainingCorpus[i++];

				try {
					for (Token proofToken : proofSentence) {
						produceAllPossibleRules(board, proofToken, trainingSentence);
					}
				} finally {
					trainingSentence.commit();
				}
			}
		}

		private void produceAllPossibleRules(ScoreBoard board, Token proofToken, DelayedContext trainingSentence) {
			Token trainingToken = trainingSentence.next();

			if (!ObjectUtils.equals(proofToken.getTag(), trainingToken.getTag())) {
				Collection<Rule> localPossibleRules = produceAllPossibleRules(trainingSentence, proofToken);
				
				for (Rule localPossibleRule : localPossibleRules) {
					board.addTruePositive(localPossibleRule);
				}
			}
		}
		
		private Collection<Rule> produceAllPossibleRules(Context context, Token target) {
			Collection<Rule> rules = new LinkedHashSet<Rule>(ruleFactories.size());

			for (RuleFactory factory : ruleFactories)
				try {
					rules.add(factory.create(context, target));
				} catch (RuleCreationException e) {
				}

			return rules;
		}

		private Score selectBestRule(Queue<Score> possibleRules) {
			Score bestScore = new Score(null, null);
			bestScore.dec();

			while(!possibleRules.isEmpty()) {
				Score entry = possibleRules.poll();
				
				if (entry.getScore() > bestScore.getScore()) {
					computeNegativeScore(entry);
		
					if (entry.getScore() > bestScore.getScore()) {
						bestScore = entry;
					}
				} else
					break; // cut
			}

			return bestScore;
		}

		private void computeNegativeScore(Score entry) {
			if (!entry.negativeMatchesComputed()) {
				entry.dec();
				
				int i = 0;
				for (Sentence proofSentence : proofCorpus) {
					DelayedContext trainingSentence = trainingCorpus[i++];
					computeNegativeScore(entry, proofSentence, trainingSentence);
				}
			}
		}

		private void computeNegativeScore(Score score, Sentence proofSentence, DelayedContext trainingSentence) {
			try {
				for (Token proofToken : proofSentence) {
					trainingSentence.next();

					computeNegativeScore(score, proofToken, trainingSentence);
				}
			} finally {
				trainingSentence.commit();
			}
		}

		private void computeNegativeScore(Score score, Token proofToken, DelayedContext trainingSentence) {
			Rule rule = score.rule;

			if (rule.matches(trainingSentence))
				if (ObjectUtils.equals(rule.getFrom(), proofToken.getTag()))
					score.dec();
		}


	}
	
	public static class Score implements Comparable<Score> {
		public final Object roundCreated;
		public Object roundComputed;
		
		public final Rule rule;

		private int positiveMatches = 0;
		private int negativeMatches = -1;
		
		protected Score(Object roundCreated, Rule rule) {
			this.roundCreated = roundCreated;
			this.rule = rule;
		}
		
		public void inc() {
			positiveMatches++;
		}
		
		public void dec() {
			negativeMatches++;
		}
		
		public boolean negativeMatchesComputed() {
			return negativeMatches != -1;
		}
		
		public int getScore() {
			return positiveMatches - negativeMatches;
		}

		public int compareTo(Score o) {
			return o.getScore() - getScore();
		}
	}

	public static class ScoreBoard {
		private final Map<Rule, Score> rules = new HashMap<Rule, Score>();
		private Object round;
		
		public void addTruePositive(Rule rule) {
			Score score = rules.get(rule);
			
			if (score == null) {
				score = new Score(round, rule);
				rules.put(rule, score);
			}
			
			if (round == score.roundCreated)
				score.inc();
		}

		public Iterator<Rule> iterator() {
			return rules.keySet().iterator();
		}
		
		public Object newRound() {
			return round = new Object();
		}

		public Queue<Score> getRulesByPriority() {
			return new PriorityQueue<Score>(rules.values());
		}		
	}
}
