package br.eti.rslemos.brill.events;

import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.tagger.Sentence;

public abstract privileged aspect BrillTrainerPointcuts {
	
	public pointcut onTraining(BrillTrainer trainer, List<Sentence> overCorpus):
		this(trainer) && 
		execution(public * BrillTrainer+.train(List+)) && args(overCorpus) &&
		within(BrillTrainer+);

	public pointcut onPreparing(BrillTrainer trainer, List<Sentence> overCorpus):
		execution(void BrillTrainer+.prepareTrainingCorpus()) && 
		cflow(onTraining(trainer, overCorpus)) && within(BrillTrainer+);
	
}
