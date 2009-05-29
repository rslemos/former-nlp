package br.eti.rslemos.brill.stats;

import java.util.List;

import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.brill.RulesetTrainer;
import br.eti.rslemos.brill.Token;

public privileged aspect TrainerProgressAspect percflow(reportingTraining()) {
	
	private pointcut reportingTraining(): execution(RuleBasedTagger RulesetTrainer.train(List, TrainerProgressListener));
	
	protected TrainerProgressListener listener;
	
	public RuleBasedTagger RulesetTrainer.train(List<List<Token>> proofCorpus, TrainerProgressListener listener) {
		TrainerProgressAspect.aspectOf().listener = listener;
		return train(proofCorpus);
	}

	private pointcut training(): execution(RuleBasedTagger RulesetTrainer.train(List));

	before(): training() {
		listener.trainingStarted();
	}
	
	after() returning (RuleBasedTagger tagger): training() {
		listener.trainingFinished();
	}
	
	
}
