package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV2WDRule extends AbstractRule implements Rule {

	private final String prev2Word;

	public PREV2WDRule(String from, String to, String prev2Word) {
		super(from, to);
		this.prev2Word = prev2Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word_2 = context.getToken(-2).getWord();
		
		return prev2Word != null ? prev2Word.equals(word_2) : word_2 == null;
	}

}