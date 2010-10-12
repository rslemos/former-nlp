package br.eti.rslemos.brill.events;

public interface BrillTrainerListener {

	void trainingStart(BrillTrainerEvent event);

	void trainingFinish(BrillTrainerEvent event);

	void workingCorpusInitializationStart(BrillTrainerEvent event);

	void workingCorpusInitializationFinish(BrillTrainerEvent event);

	void ruleDiscoveryPhaseStart(BrillTrainerEvent event);

	void ruleDiscoveryPhaseFinish(BrillTrainerEvent event);

	void ruleDiscoveryRoundStart(BrillTrainerEvent event);

	void ruleDiscoveryRoundFinish(BrillTrainerEvent event);

	void possibleRulesProductionStart(BrillTrainerEvent event);

	void possibleRulesProductionFinish(BrillTrainerEvent event);

	void bestRuleSelectionStart(BrillTrainerEvent event);

	void bestRuleSelectionFinish(BrillTrainerEvent event);

}
