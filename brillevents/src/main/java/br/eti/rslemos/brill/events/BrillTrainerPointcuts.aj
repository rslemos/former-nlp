package br.eti.rslemos.brill.events;

import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;

public abstract privileged aspect BrillTrainerPointcuts {
	
	public pointcut onTraining(BrillTrainer trainer, List<Sentence> overCorpus):
		this(trainer) && 
		execution(public * BrillTrainer+.train(List+)) && args(overCorpus) &&
		within(BrillTrainer+);

	public pointcut onPreparing(BrillTrainer trainer):
		execution(void BrillTrainer+.prepareTrainingCorpus()) && 
		cflow(onTraining(trainer, *)) && within(BrillTrainer+);
	
	public pointcut onBaseTagging(BrillTrainer trainer, Sentence onSentence):
		call(void Tagger+.tag(Sentence+)) && args(onSentence) &&
		cflow(onTraining(trainer, *)) && within(BrillTrainer+);
	
	public pointcut onRuleDiscovery(BrillTrainer trainer):
		execution(void BrillTrainer+.discoverRules()) && 
		cflow(onTraining(trainer, *)) && within(BrillTrainer+);
}
