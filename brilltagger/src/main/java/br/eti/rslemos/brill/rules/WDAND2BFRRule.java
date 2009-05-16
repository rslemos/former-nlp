package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2BFRRule extends AbstractRule implements Rule {

	private final String prev2Word;
	private final String word;

	public WDAND2BFRRule(String from, String to, String prev2Word, String word) {
		super(from, to);
		this.prev2Word = prev2Word;
		this.word = word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word_2 = context.getToken(-2).getWord();
		String word0 = context.getToken(0).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prev2Word != null ? prev2Word.equals(word_2) : word_2 == null);
	}
}
