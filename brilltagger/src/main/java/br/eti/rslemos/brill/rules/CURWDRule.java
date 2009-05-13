package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class CURWDRule extends AbstractRule {

	private final String word;

	public CURWDRule(String from, String to, String word) {
		super(from, to);
		this.word = word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		
		return word != null ? word.equals(word0) : word0 == null;
	}

}
