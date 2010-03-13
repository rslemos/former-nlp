package br.eti.rslemos.brill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class RulesetTrainer<T> {
	private final Tagger<T> baseTagger;

	private final List<RuleFactory<T>> ruleFactories;
	private final int threshold;

	public RulesetTrainer(Tagger<T> baseTagger, List<RuleFactory<T>> ruleFactories) {
		this(baseTagger, ruleFactories, 1);
	}

	public RulesetTrainer(Tagger<T> baseTagger, List<RuleFactory<T>> ruleFactories, int threshold) {
		this.baseTagger = baseTagger;
		this.ruleFactories = ruleFactories;
		this.threshold = threshold;
	}

	private TrainingContext createTrainingContext(List<Sentence<T>> proofCorpus) {
		return new TrainingContext(proofCorpus);
	}

	public synchronized RuleBasedTagger<T> train(List<Sentence<T>> proofCorpus) {
		TrainingContext trainingContext = createTrainingContext(proofCorpus);
		
		trainingContext.applyBaseTagger();
		List<Rule<T>> rules = trainingContext.discoverRules();

		return new RuleBasedTagger<T>(baseTagger, rules);
	}

	public class TrainingContext {

		public final List<Sentence<T>> proofCorpus;
		public final List<Sentence<T>> trainingCorpus;
		
		public TrainingContext(List<Sentence<T>> proofCorpus) {
			ArrayList<Sentence<T>> proofCorpus0 = new ArrayList<Sentence<T>>(proofCorpus);
			proofCorpus0.trimToSize();
			
			this.proofCorpus = Collections.unmodifiableList(proofCorpus0);
			this.trainingCorpus = new ArrayList<Sentence<T>>(proofCorpus.size());
		}

		private void applyBaseTagger() {
			for (Sentence<T> proofSentence : proofCorpus) {
				Sentence<T> trainingSentence = new DefaultSentence<T>(proofSentence);
				baseTagger.tag(trainingSentence);
				trainingCorpus.add(trainingSentence);
			}
		}

		public List<Rule<T>> discoverRules() {
			ArrayList<Rule<T>> rules = new ArrayList<Rule<T>>();

			ScoreBoard<T> board = new ScoreBoard<T>();
			do {
				board.newRound();
				
				produceAllPossibleRules(board);
				
				Score<T> bestScore = selectBestRule(board.getRulesByPriority());
				Rule<T> bestRule = bestScore.rule;
				
				if (bestRule != null) {
					applyRule(bestRule);

					if (bestScore.getScore() >= threshold) {
						rules.add(bestRule);
						for (Iterator<Rule<T>> iterator = board.iterator(); iterator.hasNext();) {
							Rule<T> rule = iterator.next();
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

			rules.trimToSize();
			
			return rules;
		}

		private void applyRule(Rule<T> bestRule) {
			for (Sentence<T> trainingSentence : trainingCorpus)
				RuleBasedTagger.applyRule(new DelayedContext<T>(new SentenceContext<T>(trainingSentence)), bestRule);
		}

		private void produceAllPossibleRules(ScoreBoard<T> board) {
			for (Pair<Sentence<T>, Sentence<T>> pair : pairOf(proofCorpus, trainingCorpus)) {
				produceAllPossibleRules(board, pair.x, pair.y);
			}
		}

		private void produceAllPossibleRules(ScoreBoard<T> board, Sentence<T> proofSentence, Sentence<T> trainingSentence) {
			Context<T> trainingContext = new SentenceContext<T>(trainingSentence);
			
			for (Token<T> proofToken : proofSentence) {
				Token<T> trainingToken = trainingContext.next();
				
				if (!ObjectUtils.equals(proofToken.getTag(), trainingToken.getTag())) {
					Collection<Rule<T>> localPossibleRules = produceAllPossibleRules(trainingContext, proofToken);
					
					for (Rule<T> localPossibleRule : localPossibleRules) {
						board.addTruePositive(localPossibleRule);
					}
				}
			}
		}

		private Collection<Rule<T>> produceAllPossibleRules(Context<T> context, Token<T> target) {
			Collection<Rule<T>> rules = new LinkedHashSet<Rule<T>>(ruleFactories.size());

			for (RuleFactory<T> factory : ruleFactories)
				rules.addAll(factory.create(context, target));

			return rules;
		}

		private Score<T> selectBestRule(Queue<Score<T>> possibleRules) {
			Score<T> bestScore = new Score<T>(null, null);
			bestScore.dec();

			while(!possibleRules.isEmpty()) {
				Score<T> entry = possibleRules.poll();
				
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

		private void computeNegativeScore(Score<T> entry) {
			if (!entry.negativeMatchesComputed()) {
				entry.dec();
				
				for (Pair<Sentence<T>, Sentence<T>> pair : pairOf(proofCorpus, trainingCorpus)) {
					computeNegativeScore(entry, pair.x, pair.y);
				}
			}
		}

		private void computeNegativeScore(Score<T> entry, Sentence<T> proofSentence, Sentence<T> trainingSentence) {
			Context<T> trainingContext = new SentenceContext<T>(trainingSentence);
			
			for (Token<T> proofToken : proofSentence) {
				trainingContext.next();
			
				computeNegativeScore(entry, proofToken, trainingContext);
			}
		}

		private void computeNegativeScore(Score<T> score, Token<T> proofToken, Context<T> trainingSentence) {
			Rule<T> rule = score.rule;

			if (rule.matches(trainingSentence))
				if (ObjectUtils.equals(rule.getFrom(), proofToken.getTag()))
					score.dec();
		}


	}
	
	public static class Score<T1> implements Comparable<Score<T1>> {
		public final Object roundCreated;
		public Object roundComputed;
		
		public final Rule<T1> rule;

		private int positiveMatches = 0;
		private int negativeMatches = -1;
		
		protected Score(Object roundCreated, Rule<T1> rule) {
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

		public int compareTo(Score<T1> o) {
			return o.getScore() - getScore();
		}
	}

	public static class ScoreBoard<T1> {
		private final Map<Rule<T1>, Score<T1>> rules = new HashMap<Rule<T1>, Score<T1>>();
		private Object round;
		
		public void addTruePositive(Rule<T1> rule) {
			Score<T1> score = rules.get(rule);
			
			if (score == null) {
				score = new Score<T1>(round, rule);
				rules.put(rule, score);
			}
			
			if (round == score.roundCreated)
				score.inc();
		}

		public Iterator<Rule<T1>> iterator() {
			return rules.keySet().iterator();
		}
		
		public Object newRound() {
			return round = new Object();
		}

		public Queue<Score<T1>> getRulesByPriority() {
			return new PriorityQueue<Score<T1>>(rules.values());
		}		
	}
	
	private static class Pair<X, Y> {
		public final X x;
		public final Y y;

		public Pair(X x, Y y) {
			this.x = x;
			this.y = y;
		}
	}

	private static <X, Y> Iterable<Pair<X, Y>> pairOf(final Iterable<X> x, final Iterable<Y> y) {
		return new Iterable<Pair<X, Y>>() {

			@Override
			public Iterator<Pair<X, Y>> iterator() {
				return pairOf(x.iterator(), y.iterator());
			}
			
		};
	}
	
	private static <X, Y> Iterator<Pair<X, Y>> pairOf(final Iterator<X> x, final Iterator<Y> y) {
		return new Iterator<Pair<X, Y>>() {

			@Override
			public boolean hasNext() {
				return x.hasNext() && y.hasNext();
			}

			@Override
			public Pair<X, Y> next() {
				return new Pair<X, Y>(x.next(), y.next());
			}

			@Override
			public void remove() {
				x.remove();
				y.remove();
			}
			
		};
	}
	
}
