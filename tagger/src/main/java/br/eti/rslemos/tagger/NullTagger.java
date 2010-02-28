package br.eti.rslemos.tagger;

import java.io.Serializable;


public class NullTagger<T> implements Tagger<T>, Serializable {
	
	private static final long serialVersionUID = -659510706597170785L;
	
	public NullTagger() {
	}

	public void tag(Sentence<T> sentence) {
	}
}