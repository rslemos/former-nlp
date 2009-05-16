package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class NEXT2WDRule extends AbstractRule {

	private final String next2Word;

	public NEXT2WDRule(String from, String to, String next2Word) {
		super(from, to);
		
		this.next2Word = next2Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word2 = context.getToken(2).getWord();
		
		return next2Word != null ? next2Word.equals(word2) : word2 == null;
	}
}
