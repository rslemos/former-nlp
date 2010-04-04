package br.eti.rslemos.brill.events;

public interface BrillTrainerListener {

	void trainingStart(BrillTrainerEvent event);

	void trainingFinish(BrillTrainerEvent event);

	void workingCorpusInitializationStart(BrillTrainerEvent event);

	void workingCorpusInitializationFinish(BrillTrainerEvent event);

	void baseTaggingStart(BrillTrainerEvent event);

	void baseTaggingFinish(BrillTrainerEvent event);

	void ruleDiscoveryStart(BrillTrainerEvent event);

	void ruleDiscoveryFinish(BrillTrainerEvent event);


}
