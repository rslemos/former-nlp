package br.eti.rslemos.brill.events;

public interface BrillTrainerListener {

	void trainingStart(BrillTrainerEvent event);

	void trainingFinish(BrillTrainerEvent event);

}
