package br.eti.rslemos.brill;

import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;

import br.eti.rslemos.brill.RulesetTrainer.RuleSelectStrategy;
import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

public class ScoringRuleSelectStrategy implements RuleSelectStrategy {
	public static interface ScoringStrategy {
		void setTrainingContext(TrainingContext trainingContext);
		int compute(Rule rule, int positiveScore);
	}
	
	private final ScoringStrategy scoringStrategy;
	
	public ScoringRuleSelectStrategy(ScoringStrategy scoringStrategy) {
		this.scoringStrategy = scoringStrategy;
	}

	public void setTrainingContext(TrainingContext trainingContext) {
		scoringStrategy.setTrainingContext(trainingContext);
	}

	public Rule selectBestRule(Queue<Map.Entry<Rule, Integer>> possibleRules) {
		Rule bestRule = null;
		int bestScore = 0;

		while(!possibleRules.isEmpty()) {
			Map.Entry<Rule, Integer> entry = possibleRules.poll();
			
			Rule rule = entry.getKey();
			int positiveScore = entry.getValue();

			if (positiveScore > bestScore) {
				int score = scoringStrategy.compute(rule, positiveScore);
	
				if (score > bestScore) {
					bestRule = rule;
					bestScore = score;
				}
			} else
				break; // cut
		}

		return bestRule;
	}

}
