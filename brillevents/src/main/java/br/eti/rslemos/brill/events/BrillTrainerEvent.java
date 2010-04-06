package br.eti.rslemos.brill.events;

import java.util.EventObject;
import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
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
		result.append(']');
		
		return result.toString();
	}
	
	
}
