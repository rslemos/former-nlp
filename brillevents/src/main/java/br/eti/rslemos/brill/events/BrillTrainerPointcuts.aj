package br.eti.rslemos.brill.events;

import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.BrillTrainer.Score;

public abstract privileged aspect BrillTrainerPointcuts {
	
	public pointcut onTraining(BrillTrainer trainer, List<Sentence> baseCorpus, List<Sentence> proofCorpus):
		this(trainer) && 
		execution(public * BrillTrainer+.train(List+, List+)) && args(baseCorpus, proofCorpus) &&
		within(BrillTrainer+);

	public pointcut onPreparing(BrillTrainer trainer):
		this(trainer) &&
		execution(void BrillTrainer+.prepareTrainingCorpus()) && 
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
	
	public pointcut onBestRuleSelection(BrillTrainer trainer):
		this(trainer) &&
		execution(Score BrillTrainer+.selectBestRule()) && 
		within(BrillTrainer+);
	
}
