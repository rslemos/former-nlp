package br.eti.rslemos.brill;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;
import br.eti.rslemos.brill.rules.RuleCreationException;
import br.eti.rslemos.brill.rules.RuleFactory;

public class RulesetTrainer {

	private final Tagger baseTagger;
	private final List<RuleFactory> ruleFactories;
	
	private final ScoreFunction scoreFunction;


	public RulesetTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories) {
		this(baseTagger, ruleFactories, new BrillScoreFunction());
	}

	public RulesetTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories, ScoreFunction scoreFunction) {
		this.baseTagger = baseTagger;
		this.ruleFactories = ruleFactories;
		this.scoreFunction = scoreFunction;
	}

	public RuleBasedTagger train(List<List<Token>> proofCorpus) {
		TrainingContext trainingContext = new TrainingContext(proofCorpus);

		trainingContext.applyBaseTagger();
		List<Rule> rules = trainingContext.discoverRules();
		
		return new RuleBasedTagger(baseTagger, rules);
	}

	private class TrainingContext {

		private final List<List<Token>> proofCorpus;
		private BufferingContext[] workCorpus;

		public TrainingContext(List<List<Token>> proofCorpus) {
			this.proofCorpus = proofCorpus;
		}
		
		public void applyBaseTagger() {
			workCorpus = new BufferingContext[proofCorpus.size()];
			
			for (int i = 0; i < workCorpus.length; i++) {
				List<Token> proofSentence = proofCorpus.get(i);
				
				Token[] baseTaggedSentence = new DefaultToken[proofSentence.size()];
				for (int j = 0; j < baseTaggedSentence.length; j++) {
					baseTaggedSentence[j] = new DefaultToken(proofSentence.get(j).getWord());
				}
				
				baseTagger.tagSentence(Arrays.asList(baseTaggedSentence));
				workCorpus[i] = RuleBasedTagger.prepareContext(baseTaggedSentence);
			}
		}

		public List<Rule> discoverRules() {
			LinkedList<Rule> rules = new LinkedList<Rule>();
			
			while(countErrors() > 0) {
				Rule bestRule = selectBestRule(produceAllPossibleRules());

				for (BufferingContext workSentence : workCorpus)
					RuleBasedTagger.applyRule(workSentence, bestRule);
				
				rules.add(bestRule);
			}
			return rules;
		}
		
		private Rule selectBestRule(Set<Rule> possibleRules) {
			
			Rule bestRule = null;
			int bestScore = 0;
			
			for (Rule rule : possibleRules) {
				
				int score = scoreFunction.compute(proofCorpus, workCorpus, rule);

				if (score > bestScore) {
					bestRule = rule;
					bestScore = score;
				}
			}

			return bestRule;
		}

		private Set<Rule> produceAllPossibleRules() {
			Set<Rule> allPossibleRules = new HashSet<Rule>();
			
			int i = 0;
			for (List<Token> proofSentence : proofCorpus) {
				BufferingContext workSentence = workCorpus[i++];

				try {
					for (Token proofToken : proofSentence) {
						Token workToken = workSentence.getToken(0);
						
						if (!ObjectUtils.equals(proofToken.getTag(), workToken.getTag())) {
							Collection<Rule> localPossibleRules = produceAllPossibleRules(workSentence, proofToken);
							allPossibleRules.addAll(localPossibleRules);
						}
		
						workSentence.advance();
					}
				} finally {
					workSentence.reset();
				}
			}
			
			return allPossibleRules;
		}

		private Collection<Rule> produceAllPossibleRules(Context context, Token target) {
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

		private int countErrors() {
			int errorCount = 0;
			
			int i = 0;
			for (List<Token> proofSentence : proofCorpus) {
				BufferingContext workSentence = workCorpus[i++];

				int j = 0;
				for (Token proofToken : proofSentence) {
					Token workToken = workSentence.getToken(j++);
					
					if (!ObjectUtils.equals(proofToken.getTag(), workToken.getTag()))
						errorCount++;
				}
			}
			
			return errorCount;
		}
	}
}
