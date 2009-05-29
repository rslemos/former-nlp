package br.eti.rslemos.brill;

import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

public interface ScoringStrategy {
	void setTrainingContext(TrainingContext trainingContext);
	int compute(Rule rule);
}