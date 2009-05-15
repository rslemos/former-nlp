package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class RBIGRAMRule extends AbstractRule implements Rule {

	private final String word;
	private final String nextWord;

	public RBIGRAMRule(String from, String to, String word, String nextWord) {
		super(from, to);
		this.word = word;
		this.nextWord = nextWord;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String word1 = context.getToken(1).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(nextWord != null ? nextWord.equals(word1) : word1 == null);
	}
}
