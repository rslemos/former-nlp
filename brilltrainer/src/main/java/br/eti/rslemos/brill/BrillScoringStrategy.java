package br.eti.rslemos.brill;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;

public class BrillScoringStrategy implements ScoringStrategy {
	public int compute(List<List<Token>> proofCorpus, BufferingContext[] workCorpus, Rule rule) {
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
		return score;
	}
}