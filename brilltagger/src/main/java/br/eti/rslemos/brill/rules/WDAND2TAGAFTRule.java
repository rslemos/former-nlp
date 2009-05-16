package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGAFTRule extends AbstractRule implements Rule {

	private final String word;
	private final String next2Tag;

	public WDAND2TAGAFTRule(String from, String to, String word, String next2Tag) {
		super(from, to);
		this.word = word;
		this.next2Tag = next2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String tag2 = context.getToken(2).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next2Tag != null ? next2Tag.equals(tag2) : tag2 == null);
	}
}
