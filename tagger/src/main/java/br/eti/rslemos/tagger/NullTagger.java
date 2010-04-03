package br.eti.rslemos.tagger;

import java.io.Serializable;


public class NullTagger implements Tagger, Serializable {
	
	private static final long serialVersionUID = -659510706597170785L;
	
	public NullTagger() {
	}

	public void tag(Sentence sentence) {
	}
}