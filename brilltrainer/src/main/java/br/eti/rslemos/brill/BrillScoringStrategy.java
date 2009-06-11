package br.eti.rslemos.brill;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;
import br.eti.rslemos.brill.RulesetTrainer.Score;
import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;
import br.eti.rslemos.brill.ScoringRuleSelectStrategy.ScoringStrategy;

public class BrillScoringStrategy implements ScoringStrategy {
	private TrainingContext trainingContext;

	public void setTrainingContext(TrainingContext trainingContext) {
		this.trainingContext = trainingContext;
	}

	public void compute(Score score) {
		int i = 0;
		for (List<Token> proofSentence : trainingContext.proofCorpus) {
			BufferingContext trainingSentence = trainingContext.trainingCorpus[i++];
			computeNegativeScore(score, proofSentence, trainingSentence);
		}
	}

	private void computeNegativeScore(Score score, List<Token> proofSentence, BufferingContext trainingSentence) {
		Rule rule = score.rule;
		try {
			for (Token proofToken : proofSentence) {
				trainingSentence.next();
				
				if (rule.matches(trainingSentence))
					if (ObjectUtils.equals(rule.getFrom(), proofToken.getTag()))
						score.dec();
			}
		} finally {
			trainingSentence.reset();
		}
	}
}
