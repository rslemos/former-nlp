package br.eti.rslemos.brill.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
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
	private static final Method TRAINING_CORPUS_INITIALIZED;
	private static final Method BASE_TAGGER_APPLIED;
	
	static {
		Class<BrillTrainerListener> clazz = BrillTrainerListener.class;
		Class<?>[] args = new Class[] {BrillTrainerEvent.class};
		
		try {
			TRAINING_START = clazz.getMethod("trainingStart", args);
			TRAINING_FINISH = clazz.getMethod("trainingFinish", args);
			TRAINING_CORPUS_INITIALIZED = clazz.getMethod("trainingCorpusInitialized", args);
			BASE_TAGGER_APPLIED = clazz.getMethod("baseTaggerApplied", args);
		} catch (Exception e) {
			throw (Error)(new LinkageError().initCause(e));
		}
	}

	before(BrillTrainer trainer, List<Sentence> overCorpus): onTraining(trainer, overCorpus) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setOverCorpus(overCorpus);
		
		trainer.fireNotification(TRAINING_START, prototype);
	}

	after(BrillTrainer trainer, List<Sentence> overCorpus) returning: onTraining(trainer, overCorpus) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setOverCorpus(overCorpus);
		
		trainer.fireNotification(TRAINING_FINISH, prototype);
	}
	
	after(BrillTrainer trainer, List<Sentence> overCorpus) returning: onPreparing(trainer, overCorpus) { 
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setOverCorpus(overCorpus);
		prototype.setTrainingCorpus(trainer.trainingCorpus);
		
		trainer.fireNotification(TRAINING_CORPUS_INITIALIZED, prototype);
	}
	
	after(BrillTrainer trainer, List<Sentence> overCorpus, Sentence onSentence) returning: onBaseTagging(trainer, overCorpus, onSentence) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setOverCorpus(overCorpus);
		prototype.setLastProcessedSentence(onSentence);
		
		trainer.fireNotification(BASE_TAGGER_APPLIED, prototype);
	}
}
