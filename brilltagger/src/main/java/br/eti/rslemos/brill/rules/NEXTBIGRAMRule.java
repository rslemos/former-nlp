package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTBIGRAMRule extends AbstractRule implements Rule {

	private final String next1Word;
	private final String next2Word;

	public NEXTBIGRAMRule(String from, String to, String next1Word, String next2Word) {
		super(from, to);
		this.next1Word = next1Word;
		this.next2Word = next2Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();
		
		return (next1Word != null ? next1Word.equals(word1) : word1 == null) &&
			(next2Word != null ? next2Word.equals(word2) : word2 == null);
	}
}
