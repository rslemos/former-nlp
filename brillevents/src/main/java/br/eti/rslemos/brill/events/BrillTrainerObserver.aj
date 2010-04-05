package br.eti.rslemos.brill.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Sentence;

public privileged aspect BrillTrainerObserver extends BrillTrainerPointcuts {
	private List<BrillTrainerListener> BrillTrainer.listeners = new ArrayList<BrillTrainerListener>();
	
	public void BrillTrainer.addBrillTrainerListener(BrillTrainerListener listener) {
		listeners.add(listener);
	}
	
	public void BrillTrainer.removeBrillTrainerListener(BrillTrainerListener listener) {
		listeners.remove(listener);
	}

	private void BrillTrainer.fireNotification(Method method, BrillTrainerEvent prototype) {
		ObserverUtils.fireNotification(listeners, method, prototype);
	}

	private static final Method TRAINING_START;
	private static final Method TRAINING_FINISH;
	private static final Method WORKING_CORPUS_INITIALIZATION_START;
	private static final Method WORKING_CORPUS_INITIALIZATION_FINISH;
	private static final Method BASE_TAGGING_START;
	private static final Method BASE_TAGGING_FINISH;
	private static final Method RULE_DISCOVERY_PHASE_START;
	private static final Method RULE_DISCOVERY_PHASE_FINISH;
	private static final Method RULE_DISCOVERY_ROUND_START;
	private static final Method RULE_DISCOVERY_ROUND_FINISH;

	static {
		Class<BrillTrainerListener> clazz = BrillTrainerListener.class;
		Class<?>[] args = new Class[] {BrillTrainerEvent.class};
		
		try {
			TRAINING_START = clazz.getMethod("trainingStart", args);
			TRAINING_FINISH = clazz.getMethod("trainingFinish", args);
			WORKING_CORPUS_INITIALIZATION_START = clazz.getMethod("workingCorpusInitializationStart", args);
			WORKING_CORPUS_INITIALIZATION_FINISH = clazz.getMethod("workingCorpusInitializationFinish", args);
			BASE_TAGGING_START = clazz.getMethod("baseTaggingStart", args);
			BASE_TAGGING_FINISH = clazz.getMethod("baseTaggingFinish", args);
			RULE_DISCOVERY_PHASE_START = clazz.getMethod("ruleDiscoveryPhaseStart", args);
			RULE_DISCOVERY_PHASE_FINISH = clazz.getMethod("ruleDiscoveryPhaseFinish", args);
			RULE_DISCOVERY_ROUND_START = clazz.getMethod("ruleDiscoveryRoundStart", args);
			RULE_DISCOVERY_ROUND_FINISH = clazz.getMethod("ruleDiscoveryRoundFinish", args);
		} catch (Exception e) {
			throw (Error)(new LinkageError().initCause(e));
		}
	}

	before(BrillTrainer trainer, List<Sentence> proofCorpus): onTraining(trainer, proofCorpus) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(proofCorpus);
		
		trainer.fireNotification(TRAINING_START, prototype);
	}

	after(BrillTrainer trainer, List<Sentence> proofCorpus) returning: onTraining(trainer, proofCorpus) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(proofCorpus);
		
		trainer.fireNotification(TRAINING_FINISH, prototype);
	}

	private int BrillTrainer.baseTaggerSentenceCounter;
	
	before(BrillTrainer trainer): onPreparing(trainer) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		
		trainer.fireNotification(WORKING_CORPUS_INITIALIZATION_START, prototype);
		
		trainer.baseTaggerSentenceCounter = -1;
	}

	after(BrillTrainer trainer) returning: onPreparing(trainer) { 
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setWorkingCorpus(trainer.trainingCorpus);
		
		trainer.fireNotification(WORKING_CORPUS_INITIALIZATION_FINISH, prototype);
	}
	
	before(BrillTrainer trainer, Sentence onSentence): onBaseTagging(trainer, onSentence) {
		trainer.baseTaggerSentenceCounter++;
		
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setCurrentSentenceIndex(trainer.baseTaggerSentenceCounter);
		prototype.setCurrentSentence(onSentence);
		
		trainer.fireNotification(BASE_TAGGING_START, prototype);
	}

	after(BrillTrainer trainer, Sentence onSentence) returning: onBaseTagging(trainer, onSentence) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setCurrentSentenceIndex(trainer.baseTaggerSentenceCounter);
		prototype.setCurrentSentence(onSentence);
		
		trainer.fireNotification(BASE_TAGGING_FINISH, prototype);
	}

	before(BrillTrainer trainer): onRuleDiscoveryPhase(trainer) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setWorkingCorpus(trainer.trainingCorpus);
		
		trainer.fireNotification(RULE_DISCOVERY_PHASE_START, prototype);
	}

	after(BrillTrainer trainer) returning: onRuleDiscoveryPhase(trainer) { 
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setWorkingCorpus(trainer.trainingCorpus);
		prototype.setFoundRules(trainer.rules);
		
		trainer.fireNotification(RULE_DISCOVERY_PHASE_FINISH, prototype);
	}
	
	before(BrillTrainer trainer): onRuleDiscoveryRound(trainer) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setWorkingCorpus(trainer.trainingCorpus);
		prototype.setRound(trainer.board.round);
		prototype.setFoundRules(trainer.rules);

		trainer.fireNotification(RULE_DISCOVERY_ROUND_START, prototype);
	}

	after(BrillTrainer trainer) returning(Rule rule): onRuleDiscoveryRound(trainer) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setWorkingCorpus(trainer.trainingCorpus);
		prototype.setRound(trainer.board.round);
		prototype.setFoundRules(trainer.rules);
		prototype.setNewRule(rule);

		trainer.fireNotification(RULE_DISCOVERY_ROUND_FINISH, prototype);
	}
}
