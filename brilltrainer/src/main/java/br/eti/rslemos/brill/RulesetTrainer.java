package br.eti.rslemos.brill;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.eti.rslemos.brill.Tagger.BufferingContext;
import br.eti.rslemos.brill.rules.CURWDRule;

public class RulesetTrainer {

	private final BaseTagger baseTagger;

	public RulesetTrainer(BaseTagger baseTagger) {
		this.baseTagger = baseTagger;
	}

	public List<Rule> train(List<List<Token>> sentences) {
		BufferingContext[] workSentences = getBaseTaggedSentences(sentences);
		
		LinkedList<Rule> rules = new LinkedList<Rule>();
		
		while(countErrors(sentences, workSentences) > 0) {
			List<List<Token>> sentences1 = sentences;
			BufferingContext[] sentences2 = workSentences;
			
			Set<Rule> possibleRules = produceAllPossibleRules(sentences1, sentences2);

			Rule bestRule = selectBestRule(possibleRules, sentences1, sentences2);

			for (BufferingContext workSentence : workSentences)
				Tagger.applyRule(workSentence, bestRule);
			
			rules.add(bestRule);
		}
		
		return rules;
	}

	private Rule selectBestRule(Set<Rule> possibleRules,
			List<List<Token>> sentences1, BufferingContext[] sentences2) {
		
		Rule bestRule = null;
		int bestScore = 0;
		
		for (Rule rule : possibleRules) {
			
			int score = 0;
			
			int i = 0;
			for (List<Token> sentence1 : sentences1) {
				BufferingContext sentence2 = sentences2[i++];

				try {
					for (Token token1 : sentence1) {
						if (rule.matches(sentence2))
							if (safeEquals(rule.getTo(), token1.getTag()))
								score++;
							else
								score--;
		
						sentence2.advance();
					}
				} finally {
					sentence2.reset();
				}
			}

			if (score > bestScore) {
				bestRule = rule;
				bestScore = score;
			}
		}

		return bestRule;
	}

	private Set<Rule> produceAllPossibleRules(List<List<Token>> sentences1, BufferingContext[] sentences2) {
		Set<Rule> allPossibleRules = new HashSet<Rule>();
		
		int i = 0;
		for (List<Token> sentence1 : sentences1) {
			BufferingContext sentence2 = sentences2[i++];

			try {
				for (Token token1 : sentence1) {
					Token token2 = sentence2.getToken(0);
					
					if (!safeEquals(token2.getTag(), token1.getTag())) {
						Set<Rule> localPossibleRules = produceAllPossibleRules(token1.getTag(), sentence2);
						allPossibleRules.addAll(localPossibleRules);
					}
	
					sentence2.advance();
				}
			} finally {
				sentence2.reset();
			}
		}
		
		return allPossibleRules;
	}

	private Set<Rule> produceAllPossibleRules(String toTag,	BufferingContext context) {
		Token token0 = context.getToken(0);
		
		return Collections.singleton((Rule)new CURWDRule(token0.getTag(), toTag, token0.getWord()));
	}

	private int countErrors(List<List<Token>> sentences1, BufferingContext[] sentences2) {
		int errorCount = 0;
		
		int i = 0;
		for (List<Token> sentence1 : sentences1) {
			BufferingContext sentence2 = sentences2[i++];

			int j = 0;
			for (Token token1 : sentence1) {
				Token token2 = sentence2.getToken(j++);
				
				if (!safeEquals(token2.getTag(), token1.getTag()))
					errorCount++;
			}
		}
		
		return errorCount;
	}

	private BufferingContext[] getBaseTaggedSentences(List<List<Token>> sentences) {
		BufferingContext[] baseTaggedSentences = new BufferingContext[sentences.size()];
		
		for (int i = 0; i < baseTaggedSentences.length; i++) {
			List<Token> sentence = sentences.get(i);
			
			DefaultToken[] baseTaggedSentence = new DefaultToken[sentence.size()];
			for (int j = 0; j < baseTaggedSentence.length; j++) {
				baseTaggedSentence[j] = new DefaultToken(sentence.get(j).getWord());
				baseTagger.tag(baseTaggedSentence[j]);
			}
			baseTaggedSentences[i] = Tagger.prepareContext(baseTaggedSentence);
		}
		
		return baseTaggedSentences;
	}

	private static final <T> boolean safeEquals(T o1, T o2) {
		return o2 != null ? o2.equals(o1) : o1 == null;
	}
}
