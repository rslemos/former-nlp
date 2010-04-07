package br.eti.rslemos.brill.events;

import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.brill.Rule;

public abstract privileged aspect BrillTrainerPointcuts {
	
	public pointcut onTraining(BrillTrainer trainer, List<Sentence> overCorpus):
		this(trainer) && 
		execution(public * BrillTrainer+.train(List+)) && args(overCorpus) &&
		within(BrillTrainer+);

	public pointcut onPreparing(BrillTrainer trainer):
		this(trainer) &&
		execution(void BrillTrainer+.prepareTrainingCorpus()) && 
		within(BrillTrainer+);
	
	public pointcut onBaseTagging(BrillTrainer trainer, Sentence onSentence):
		this(trainer) &&
		call(void Tagger+.tag(Sentence+)) && args(onSentence) &&
		within(BrillTrainer+);
	
	public pointcut onRuleDiscoveryPhase(BrillTrainer trainer):
		this(trainer) &&
		execution(void BrillTrainer+.discoverRules()) && 
		within(BrillTrainer+);
	
	public pointcut onRuleDiscoveryRound(BrillTrainer trainer):
		this(trainer) &&
		execution(Rule+ BrillTrainer+.discoverNextRule()) && 
		within(BrillTrainer+);
	
	public pointcut onPossibleRulesProduction(BrillTrainer trainer):
		this(trainer) &&
		execution(void BrillTrainer+.produceAllPossibleRules()) && 
		within(BrillTrainer+);
	
}
