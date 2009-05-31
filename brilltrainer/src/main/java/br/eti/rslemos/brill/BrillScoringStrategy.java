package br.eti.rslemos.brill;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;
import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;
import br.eti.rslemos.brill.ScoringRuleSelectStrategy.ScoringStrategy;

public class BrillScoringStrategy implements ScoringStrategy {
	private TrainingContext trainingContext;

	public void setTrainingContext(TrainingContext trainingContext) {
		this.trainingContext = trainingContext;
	}

	public int compute(Rule rule) {
		int score = 0;

		int i = 0;
		for (List<Token> proofSentence : trainingContext.proofCorpus) {
			BufferingContext trainingSentence = trainingContext.trainingCorpus[i++];
			score += compute(rule, proofSentence, trainingSentence);
		}
		
		return score;
	}

	private int compute(Rule rule, List<Token> proofSentence, BufferingContext trainingSentence) {
		try {
			int score = 0;
			
			for (Token proofToken : proofSentence) {
				trainingSentence.next();
				
				if (rule.matches(trainingSentence))
					if (ObjectUtils.equals(rule.getTo(), proofToken.getTag()))
						score++;
					else
						score--;
			}
			
			return score;
		} finally {
			trainingSentence.reset();
		}
	}
}