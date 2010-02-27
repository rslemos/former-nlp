package br.eti.rslemos.tagger;

import java.io.Serializable;


public class NullTagger implements Tagger, Serializable {
	
	private static final long serialVersionUID = -659510706597170785L;
	
	public static final NullTagger NULL_TAGGER = new NullTagger();
	
	public NullTagger() {
	}

	public void tag(Sentence sentence) {
	}
	
	private Object readResolve() {
		return NULL_TAGGER;
	}
}