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

	private Object round;
	
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

	public Object getRound() {
		return round;
	}

	public void setRound(Object round) {
		this.round = round;
	}

	public Rule getNewRule() {
		return newRule;
	}

	public void setNewRule(Rule newRule) {
		this.newRule = newRule;
	}
}
