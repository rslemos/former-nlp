package br.eti.rslemos.brill.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.tagger.Sentence;

public aspect BrillTrainerObserver extends BrillTrainerPointcuts {
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
	
	static {
		Class<BrillTrainerListener> clazz = BrillTrainerListener.class;
		Class<?>[] args = new Class[] {BrillTrainerEvent.class};
		
		try {
			TRAINING_START = clazz.getMethod("trainingStart", args);
			TRAINING_FINISH = clazz.getMethod("trainingFinish", args);
		} catch (Exception e) {
			throw (Error)(new LinkageError().initCause(e));
		}
	}

	before(BrillTrainer trainer, List<Sentence> proofCorpus): onTraining(trainer, proofCorpus) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setOverCorpus(proofCorpus);
		
		trainer.fireNotification(TRAINING_START, prototype);
	}

	after(BrillTrainer trainer, List<Sentence> proofCorpus) returning: onTraining(trainer, proofCorpus) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setOverCorpus(proofCorpus);
		
		trainer.fireNotification(TRAINING_FINISH, prototype);
	}
}
