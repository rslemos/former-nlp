package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class LBIGRAMRule extends AbstractRule implements Rule {

	private final String prevWord;
	private final String word;

	public LBIGRAMRule(String from, String to, String prevWord, String word) {
		super(from, to);
		this.prevWord = prevWord;
		this.word = word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String word_1 = context.getToken(-1).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prevWord != null ? prevWord.equals(word_1) : word_1 == null);
	}
}
