package br.eti.rslemos.tagger;


public abstract class AbstractTokenTagger implements Tagger {
	public abstract void tag(Token token);

	public final void tag(Sentence sentence) {
		for (Token token : sentence)
			tag(token);
	}
}