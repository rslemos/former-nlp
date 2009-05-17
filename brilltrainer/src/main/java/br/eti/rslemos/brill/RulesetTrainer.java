package br.eti.rslemos.brill;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.Tagger.BufferingContext;
import br.eti.rslemos.brill.rules.CURWDRule;

public class RulesetTrainer {

	private final BaseTagger baseTagger;

	public RulesetTrainer(BaseTagger baseTagger) {
		this.baseTagger = baseTagger;
	}

	public List<Rule> train(List<List<Token>> proofCorpus) {
		TrainingContext trainingContext = new TrainingContext(proofCorpus);

		trainingContext.applyBaseTagger();
		return trainingContext.discoverRules();
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
				
				DefaultToken[] baseTaggedSentence = new DefaultToken[proofSentence.size()];
				for (int j = 0; j < baseTaggedSentence.length; j++) {
					baseTaggedSentence[j] = new DefaultToken(proofSentence.get(j).getWord());
					baseTagger.tag(baseTaggedSentence[j]);
				}
				workCorpus[i] = Tagger.prepareContext(baseTaggedSentence);
			}
		}


		public List<Rule> discoverRules() {
			LinkedList<Rule> rules = new LinkedList<Rule>();
			
			while(countErrors() > 0) {
				Rule bestRule = selectBestRule(produceAllPossibleRules());

				for (BufferingContext workSentence : workCorpus)
					Tagger.applyRule(workSentence, bestRule);
				
				rules.add(bestRule);
			}
			return rules;
		}
		
		private Rule selectBestRule(Set<Rule> possibleRules) {
			
			Rule bestRule = null;
			int bestScore = 0;
			
			for (Rule rule : possibleRules) {
				
				int score = 0;
				
				int i = 0;
				for (List<Token> proofSentence : proofCorpus) {
					BufferingContext workSentence = workCorpus[i++];

					try {
						for (Token proofToken : proofSentence) {
							if (rule.matches(workSentence))
								if (ObjectUtils.equals(rule.getTo(), proofToken.getTag()))
									score++;
								else
									score--;
			
							workSentence.advance();
						}
					} finally {
						workSentence.reset();
					}
				}

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
							Set<Rule> localPossibleRules = produceAllPossibleRules(proofToken.getTag(), workSentence);
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

		private Set<Rule> produceAllPossibleRules(String toTag, BufferingContext context) {
			Token token0 = context.getToken(0);
			
			return Collections.singleton((Rule)new CURWDRule(token0.getTag(), toTag, token0.getWord()));
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
