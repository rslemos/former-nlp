package br.eti.rslemos.tagger;

public abstract class AbstractTokenTagger<T> implements Tagger<T> {
	protected AbstractTokenTagger() {	
	}
	
	public abstract void tag(Token<T> token);

	public final void tag(Sentence<T> sentence) {
		for (Token<T> token : sentence)
			tag(token);
	}
}
