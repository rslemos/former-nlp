package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVBIGRAMRule extends AbstractRule implements Rule {

	private final String prev2Word;
	private final String prev1Word;

	public PREVBIGRAMRule(String from, String to, String prev2Word, String prev1Word) {
		super(from, to);
		this.prev2Word = prev2Word;
		this.prev1Word = prev1Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word_2 = context.getToken(-2).getWord();
		String word_1 = context.getToken(-1).getWord();
		
		return (prev2Word != null ? prev2Word.equals(word_2) : word_2 == null) &&
			(prev1Word != null ? prev1Word.equals(word_1) : word_1 == null);
	}
}
