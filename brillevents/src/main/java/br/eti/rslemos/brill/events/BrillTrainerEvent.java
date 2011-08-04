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

import java.util.Collection;
import java.util.EventObject;
import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.brill.BrillTrainer.Score;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Sentence;

public class BrillTrainerEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	private List<Sentence> proofCorpus;
	private List<Sentence> workingCorpus;
	
	private Sentence currentSentence;
	private int currentSentenceIndex = -1;

	private List<Rule> foundRules;

	private int round = -1;
	
	private Rule newRule;

	private Collection<Score> possibleRules;

	public BrillTrainerEvent(BrillTrainer source) {
		super(source);
	}

	public List<Sentence> getProofCorpus() {
		return proofCorpus;
	}

	public void setProofCorpus(List<Sentence> proofCorpus) {
		this.proofCorpus = proofCorpus;
	}

	public List<Sentence> getWorkingCorpus() {
		return workingCorpus;
	}

	public void setWorkingCorpus(List<Sentence> workingCorpus) {
		this.workingCorpus = workingCorpus;
	}

	public Sentence getCurrentSentence() {
		return currentSentence;
	}

	public void setCurrentSentence(Sentence currentSentence) {
		this.currentSentence = currentSentence;
	}

	public int getCurrentSentenceIndex() {
		return currentSentenceIndex;
	}

	public void setCurrentSentenceIndex(int currentSentenceIndex) {
		this.currentSentenceIndex = currentSentenceIndex;
	}

	public List<Rule> getFoundRules() {
		return foundRules;
	}

	public void setFoundRules(List<Rule> foundRules) {
		this.foundRules = foundRules;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public Rule getNewRule() {
		return newRule;
	}

	public void setNewRule(Rule newRule) {
		this.newRule = newRule;
	}

	public Collection<Score> getPossibleRules() {
		return possibleRules;
	}
	
	public void setPossibleRules(Collection<Score> possibleRules) {
		this.possibleRules = possibleRules;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(1000);
		
		result.append(getClass().getName());
		result.append('[');
		result.append("source=").append(source).append(';');
		result.append("proofCorpus=").append(proofCorpus).append(';');
		result.append("workingCorpus=").append(workingCorpus).append(';');
		result.append("currentSentenceIndex=").append(currentSentenceIndex).append(';');
		result.append("currentSentence=").append(currentSentence).append(';');
		result.append("foundRules=").append(foundRules).append(';');
		result.append("round=").append(round).append(';');
		result.append("newRule=").append(newRule).append(';');
		result.append("possibleRules=").append(possibleRules).append(';');
		result.append(']');
		
		return result.toString();
	}
	
}
