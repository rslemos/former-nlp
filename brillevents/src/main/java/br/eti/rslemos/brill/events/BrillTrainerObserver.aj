/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.brill.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.brill.BrillTrainer.Score;

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
	private static final Method RULE_DISCOVERY_PHASE_START;
	private static final Method RULE_DISCOVERY_PHASE_FINISH;
	private static final Method RULE_DISCOVERY_ROUND_START;
	private static final Method RULE_DISCOVERY_ROUND_FINISH;
	private static final Method POSSIBLE_RULES_PRODUCTION_START;
	private static final Method POSSIBLE_RULES_PRODUCTION_FINISH;
	private static final Method BEST_RULE_SELECTION_START;
	private static final Method BEST_RULE_SELECTION_FINISH;

	static {
		Class<BrillTrainerListener> clazz = BrillTrainerListener.class;
		Class<?>[] args = new Class[] {BrillTrainerEvent.class};
		
		try {
			TRAINING_START = clazz.getMethod("trainingStart", args);
			TRAINING_FINISH = clazz.getMethod("trainingFinish", args);
			WORKING_CORPUS_INITIALIZATION_START = clazz.getMethod("workingCorpusInitializationStart", args);
			WORKING_CORPUS_INITIALIZATION_FINISH = clazz.getMethod("workingCorpusInitializationFinish", args);
			RULE_DISCOVERY_PHASE_START = clazz.getMethod("ruleDiscoveryPhaseStart", args);
			RULE_DISCOVERY_PHASE_FINISH = clazz.getMethod("ruleDiscoveryPhaseFinish", args);
			RULE_DISCOVERY_ROUND_START = clazz.getMethod("ruleDiscoveryRoundStart", args);
			RULE_DISCOVERY_ROUND_FINISH = clazz.getMethod("ruleDiscoveryRoundFinish", args);
			POSSIBLE_RULES_PRODUCTION_START = clazz.getMethod("possibleRulesProductionStart", args);
			POSSIBLE_RULES_PRODUCTION_FINISH = clazz.getMethod("possibleRulesProductionFinish", args);
			BEST_RULE_SELECTION_START = clazz.getMethod("bestRuleSelectionStart", args);
			BEST_RULE_SELECTION_FINISH = clazz.getMethod("bestRuleSelectionFinish", args);
		} catch (Exception e) {
			throw (Error)(new LinkageError().initCause(e));
		}
	}

	before(BrillTrainer trainer, List<Sentence> baseCorpus, List<Sentence> proofCorpus): onTraining(trainer, baseCorpus, proofCorpus) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(proofCorpus);
		
		trainer.fireNotification(TRAINING_START, prototype);
	}

	after(BrillTrainer trainer, List<Sentence> baseCorpus, List<Sentence> proofCorpus) returning: onTraining(trainer, baseCorpus, proofCorpus) {
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

	before(BrillTrainer trainer): onPossibleRulesProduction(trainer) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setWorkingCorpus(trainer.trainingCorpus);
		prototype.setRound(trainer.board.round);

		trainer.fireNotification(POSSIBLE_RULES_PRODUCTION_START, prototype);
	}

	after(BrillTrainer trainer) returning: onPossibleRulesProduction(trainer) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setWorkingCorpus(trainer.trainingCorpus);
		prototype.setRound(trainer.board.round);
		prototype.setPossibleRules(trainer.board.rules.values());

		trainer.fireNotification(POSSIBLE_RULES_PRODUCTION_FINISH, prototype);
	}

	before(BrillTrainer trainer): onBestRuleSelection(trainer) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setWorkingCorpus(trainer.trainingCorpus);
		prototype.setRound(trainer.board.round);
		prototype.setPossibleRules(trainer.board.rules.values());

		trainer.fireNotification(BEST_RULE_SELECTION_START, prototype);
	}

	after(BrillTrainer trainer) returning(Score bestScore): onBestRuleSelection(trainer) {
		BrillTrainerEvent prototype = new BrillTrainerEvent(trainer);
		prototype.setProofCorpus(trainer.proofCorpus);
		prototype.setWorkingCorpus(trainer.trainingCorpus);
		prototype.setRound(trainer.board.round);
		prototype.setPossibleRules(trainer.board.rules.values());

		trainer.fireNotification(BEST_RULE_SELECTION_FINISH, prototype);
	}

}
