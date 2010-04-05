package br.eti.rslemos.brill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.NullTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class BrillTrainer {
	private static final List<RuleFactory> EMPTY_FACTORY_LIST = Collections.emptyList();

	private Tagger baseTagger;

	private List<RuleFactory> ruleFactories;
	private int threshold;

	public BrillTrainer() {
		this(new NullTagger(), EMPTY_FACTORY_LIST);
	}

	public BrillTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories) {
		this(baseTagger, ruleFactories, 1);
	}

	public BrillTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories, int threshold) {
		if (threshold < 0)
			throw new IllegalArgumentException("Threshold must be non-negative");
		
		this.baseTagger = baseTagger;
		this.ruleFactories = ruleFactories;
		this.threshold = threshold;
	}
	
	public Tagger getBaseTagger() {
		return baseTagger;
	}

	public void setBaseTagger(Tagger baseTagger) {
		this.baseTagger = baseTagger;
	}

	public List<RuleFactory> getRuleFactories() {
		return ruleFactories;
	}

	public void setRuleFactories(List<RuleFactory> ruleFactories) {
		this.ruleFactories = ruleFactories;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	private transient List<Sentence> proofCorpus;
	private transient List<Sentence> trainingCorpus;

	private transient ScoreBoard board;
	private transient ArrayList<Rule> rules;

	public BrillTagger train(List<Sentence> proofCorpus) {
		this.proofCorpus = Collections.unmodifiableList(proofCorpus);
		this.trainingCorpus = new ArrayList<Sentence>(proofCorpus.size());

		this.board = new ScoreBoard();
		this.rules = new ArrayList<Rule>();

		try {
			prepareTrainingCorpus();
			discoverRules();
			
			rules.trimToSize();

			return new BrillTagger(baseTagger, rules);
		} finally {
			// dispose
			this.proofCorpus = null;
			this.trainingCorpus = null;
			this.board = null;
			this.rules = null;
		}
	}

	private void prepareTrainingCorpus() {
		for (Sentence proofSentence : proofCorpus) {
			Sentence trainingSentence = new DefaultSentence(proofSentence);
			baseTagger.tag(trainingSentence);
			trainingCorpus.add(trainingSentence);
		}
	}

	private void discoverRules() {
		Rule bestRule;
		
		while ((bestRule = discoverNextRule0()) != null) {
			rules.add(bestRule);
		}
	}

	private Rule discoverNextRule0() {
		board.newRound();
		
		return discoverNextRule();
	}

	private Rule discoverNextRule() {
		produceAllPossibleRules();
		
		Score bestScore = selectBestRule();
		
		if (bestScore.getScore() >= threshold) {
			board.discardDependentsOn(bestScore.rule);
			applyRule(bestScore.rule);

			return bestScore.rule;
		} else
			return null;
	}

	private void applyRule(Rule bestRule) {
		for (Sentence trainingSentence : trainingCorpus)
			BrillTagger.applyRule(new DelayedContext(new SentenceContext(trainingSentence)), bestRule);
	}

	private void produceAllPossibleRules() {
		for (Pair<Sentence, Sentence> pair : pairOf(proofCorpus, trainingCorpus)) {
			produceAllPossibleRules(pair.x, pair.y);
		}
	}

	private void produceAllPossibleRules(Sentence proofSentence, Sentence trainingSentence) {
		Context trainingContext = new SentenceContext(trainingSentence);
		
		for (Token proofToken : proofSentence) {
			Token trainingToken = trainingContext.next();
			
			if (!ObjectUtils.equals(proofToken.getTag(), trainingToken.getTag())) {
				Collection<Rule> rules = invokeRuleFactories(trainingContext, proofToken);
				
				board.addTruePositives(rules);
			}
		}
	}

	private Collection<Rule> invokeRuleFactories(Context context, Token target) {
		Collection<Rule> rules = new LinkedHashSet<Rule>(ruleFactories.size());

		for (RuleFactory factory : ruleFactories)
			rules.addAll(factory.create(context, target));

		return rules;
	}

	private Score selectBestRule() {
		Queue<Score> rules = board.getRulesByPriority();
		
		Score bestScore = new Score(null, null, Integer.MAX_VALUE);
		bestScore.dec();
		
		while(!rules.isEmpty()) {
			Score entry = rules.poll();
			
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
		if (entry.initNegativeMatches()) {
			for (Pair<Sentence, Sentence> pair : pairOf(proofCorpus, trainingCorpus)) {
				computeNegativeScore(entry, pair.x, pair.y);
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
	
	private static class Score implements Comparable<Score> {
		public final Object roundCreated;
		
		public final Rule rule;

		private final int counter;
		
		private int positiveMatches = 0;
		private int negativeMatches = 0;

		private boolean init = false;

		protected Score(Object roundCreated, Rule rule, int counter) {
			this.roundCreated = roundCreated;
			this.rule = rule;
			this.counter = counter;
		}
		
		public void inc() {
			positiveMatches++;
		}
		
		public void dec() {
			negativeMatches++;
		}
		
		public boolean initNegativeMatches() {
			boolean oldInit = init;
			
			init = true;
			
			return !oldInit;
		}
		
		public int getScore() {
			return positiveMatches - negativeMatches;
		}

		public int compareTo(Score o) {
			int primaryCriteria = o.getScore() - getScore();
			
			return primaryCriteria != 0 ? primaryCriteria : o.counter - counter;
		}
	}

	private static class ScoreBoard {
		private final HashMap<Rule, Score> rules = new HashMap<Rule, Score>();
		private Object round;
		
		private int counter;
		
		public void addTruePositive(Rule rule) {
			Score score = rules.get(rule);
			
			if (score == null) {
				score = new Score(round, rule, counter);
				rules.put(rule, score);
			}
			
			if (round == score.roundCreated)
				score.inc();
		}

		public void addTruePositives(Collection<Rule> rules) {
			for (Rule rule : rules) {
				addTruePositive(rule);
			}
		}
		
		public void newRound() {
			round = new Object();
		}

		public Queue<Score> getRulesByPriority() {
			return new PriorityQueue<Score>(rules.values());
		}
		
		public void discardDependentsOn(Rule rule) {
			for (Iterator<Rule> it = rules.keySet().iterator(); it.hasNext();) {
				Rule r = it.next();
				
				// already covers the case where rule == bestRule, since a rule firing depends on its tags anyway
				if (r.firingDependsOnObject(rule.getFrom()) || r.firingDependsOnObject(rule.getTo()))
					it.remove();
			}
		}
	}
	
	public static class Pair<X, Y> {
		public final X x;
		public final Y y;

		public Pair(X x, Y y) {
			this.x = x;
			this.y = y;
		}
	}

	public static <X, Y> Iterable<Pair<X, Y>> pairOf(final Iterable<X> x, final Iterable<Y> y) {
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
