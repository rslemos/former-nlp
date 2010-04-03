package br.eti.rslemos.brill.events;

import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.tagger.Sentence;

public abstract privileged aspect BrillTrainerPointcuts {
	
	public pointcut onTraining(BrillTrainer trainer, List<Sentence> proofCorpus):
		this(trainer) && 
		execution(public * BrillTrainer+.train(List)) && args(proofCorpus) &&
		within(BrillTrainer+);

}
