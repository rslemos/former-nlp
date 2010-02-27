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
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

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
		Rule[] rules = trainingContext.discoverRules();

		return new RuleBasedTagger(baseTagger, rules);
	}

	public class TrainingContext {

		public final Sentence[] proofCorpus;
		public final Sentence[] trainingCorpus;
		
		public TrainingContext(Sentence... proofCorpus) {
			this.proofCorpus = proofCorpus;
			this.trainingCorpus = new Sentence[proofCorpus.length];
		}

		public TrainingContext(List<Sentence> proofCorpus) {
			this(proofCorpus.toArray(new Sentence[proofCorpus.size()]));
		}
		
		private void applyBaseTagger() {

			for (int i = 0; i < trainingCorpus.length; i++) {
				Sentence proofSentence = proofCorpus[i];

				Token[] baseTaggedSentence = new DefaultToken[proofSentence.size()];
				for (int j = 0; j < baseTaggedSentence.length; j++) {
					baseTaggedSentence[j] = new DefaultToken(proofSentence.get(j).getWord());
				}

				trainingCorpus[i] = new DefaultSentence(baseTaggedSentence);
				baseTagger.tag(trainingCorpus[i]);
			}
		}

		public Rule[] discoverRules() {
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

			return rules.toArray(new Rule[rules.size()]);
		}

		private void applyRule(Rule bestRule) {
			for (Sentence trainingSentence : trainingCorpus)
				RuleBasedTagger.applyRule(new DelayedContext(new SentenceContext(trainingSentence)), bestRule);
		}

		private void produceAllPossibleRules(ScoreBoard board) {
			for (int i = 0; i < proofCorpus.length; i++) {
				produceAllPossibleRules(board, proofCorpus[i], trainingCorpus[i++]);
			}
		}

		private void produceAllPossibleRules(ScoreBoard board, Sentence proofSentence, Sentence trainingSentence) {
			Context trainingContext = new SentenceContext(trainingSentence);
			
			for (Token proofToken : proofSentence) {
				Token trainingToken = trainingContext.next();
				
				if (!ObjectUtils.equals(proofToken.getTag(), trainingToken.getTag())) {
					Collection<Rule> localPossibleRules = produceAllPossibleRules(trainingContext, proofToken);
					
					for (Rule localPossibleRule : localPossibleRules) {
						board.addTruePositive(localPossibleRule);
					}
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
					computeNegativeScore(entry, proofSentence, trainingCorpus[i++]);
				}
			}
		}

		private void computeNegativeScore(Score entry, Sentence proofSentence, Sentence trainingSentence) {
			Context trainingContext = new SentenceContext(trainingSentence);
			
			for (Token proofToken : proofSentence) {
				trainingContext.next();
			
				computeNegativeScore(entry, proofToken, trainingContext);
			}
		}

		private void computeNegativeScore(Score score, Token proofToken, Context trainingSentence) {
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
