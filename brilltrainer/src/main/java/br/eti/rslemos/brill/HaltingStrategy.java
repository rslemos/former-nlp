package br.eti.rslemos.brill;

import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

public interface HaltingStrategy {
	void setTrainingContext(TrainingContext trainingContext);
	boolean updateAndTest();
}