package br.eti.rslemos.brill;

import java.util.Set;

import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

public interface RuleSelectStrategy {
	void setTrainingContext(TrainingContext trainingContext);
	Rule selectBestRule(Set<Rule> possibleRules);
}