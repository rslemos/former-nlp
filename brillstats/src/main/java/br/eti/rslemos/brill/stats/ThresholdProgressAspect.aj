package br.eti.rslemos.brill.stats;

import java.util.List;

import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.brill.RulesetTrainer;
import br.eti.rslemos.brill.ThresholdHaltingStrategy;;

public abstract privileged aspect ThresholdProgressAspect percflow(reportingTraining()) {
	private pointcut reportingTraining(): execution(RuleBasedTagger RulesetTrainer.train(List)) && if(TrainerProgressAspect.aspectOf().listener instanceof ThresholdProgressListener);

	private ThresholdProgressListener listener;

	private ThresholdProgressAspect() {
		listener = (ThresholdProgressListener)TrainerProgressAspect.aspectOf().listener;
	}

	private pointcut setErrorCount(ThresholdHaltingStrategy.ThresholdHaltingStrategyContext context, int newval): 
		set(int ThresholdHaltingStrategy.ThresholdHaltingStrategyContext.errorCount) && args(newval) && target(context);

	private pointcut insideCtor():
		cflow(execution(ThresholdHaltingStrategy.ThresholdHaltingStrategyContext.new(..)));
	
	private pointcut insideUpdate():
		cflow(execution(boolean ThresholdHaltingStrategy.ThresholdHaltingStrategyContext.updateAndTest(..)));
	
	before(ThresholdHaltingStrategy.ThresholdHaltingStrategyContext context, int newval): setErrorCount(context, newval) && insideCtor() {
		listener.initialErrorCount(newval);
	}

	before(ThresholdHaltingStrategy.ThresholdHaltingStrategyContext context, int newval): setErrorCount(context, newval) && insideUpdate() {
		listener.improvementOf(context.errorCount - newval);
	}
}
