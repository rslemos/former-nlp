package br.eti.rslemos.brill.events;

import java.util.EventObject;
import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.tagger.Sentence;

public class BrillTrainerEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private List<Sentence> overCorpus;
	private List<Sentence> trainingCorpus;

	public BrillTrainerEvent(BrillTrainer source) {
		super(source);
	}

	public List<Sentence> getOverCorpus() {
		return overCorpus;
	}

	public void setOverCorpus(List<Sentence> overCorpus) {
		this.overCorpus = overCorpus;
	}

	public List<Sentence> getTrainingCorpus() {
		return trainingCorpus;
	}

	public void setTrainingCorpus(List<Sentence> trainingCorpus) {
		this.trainingCorpus = trainingCorpus;
	}

}
