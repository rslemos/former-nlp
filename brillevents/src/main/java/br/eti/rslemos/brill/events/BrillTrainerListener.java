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
