package br.eti.rslemos.brill;

import java.util.List;

public abstract class AbstractTokenTagger implements Tagger {
	public abstract void tag(Token token);

	public final void tagSentence(List<Token> sentence) {
		for (Token token : sentence)
			tag(token);
	}
}
