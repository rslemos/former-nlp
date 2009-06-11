package br.eti.rslemos.brill;

import java.util.Queue;

import br.eti.rslemos.brill.RulesetTrainer.RuleSelectStrategy;
import br.eti.rslemos.brill.RulesetTrainer.Score;
import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

public class ScoringRuleSelectStrategy implements RuleSelectStrategy {
	public static interface ScoringStrategy {
		void setTrainingContext(TrainingContext trainingContext);
		void compute(Score score);
	}
	
	private final ScoringStrategy scoringStrategy;
	
	public ScoringRuleSelectStrategy(ScoringStrategy scoringStrategy) {
		this.scoringStrategy = scoringStrategy;
	}

	public void setTrainingContext(TrainingContext trainingContext) {
		scoringStrategy.setTrainingContext(trainingContext);
	}

	public Rule selectBestRule(Object round, Queue<Score> possibleRules) {
		Rule bestRule = null;
		int bestScore = 0;

		while(!possibleRules.isEmpty()) {
			Score entry = possibleRules.poll();
			
			if (entry.getScore() > bestScore) {
				compute(round, entry);
	
				if (entry.getScore() > bestScore) {
					bestRule = entry.rule;
					bestScore = entry.getScore();
				}
			} else
				break; // cut
		}

		return bestRule;
	}

	private void compute(Object round, Score entry) {
		if (!entry.negativeMatchesComputed()) {
			entry.dec();
			scoringStrategy.compute(entry);
		}
	}

}
