package br.eti.rslemos.brill;

import java.util.Set;

import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

public class ScoringRuleSelectStrategy implements RuleSelectStrategy {
	private final ScoringStrategy scoringStrategy;
	
	public ScoringRuleSelectStrategy(ScoringStrategy scoringStrategy) {
		this.scoringStrategy = scoringStrategy;
	}

	public void setTrainingContext(TrainingContext trainingContext) {
		scoringStrategy.setTrainingContext(trainingContext);
	}

	public Rule selectBestRule(Set<Rule> possibleRules) {
		Rule bestRule = null;
		int bestScore = 0;

		for (Rule rule : possibleRules) {

			int score = scoringStrategy.compute(rule);

			if (score > bestScore) {
				bestRule = rule;
				bestScore = score;
			}
		}

		return bestRule;
	}

}
