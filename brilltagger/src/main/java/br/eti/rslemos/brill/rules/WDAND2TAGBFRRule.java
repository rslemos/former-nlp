package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGBFRRule extends AbstractRule implements Rule {

	private final String word;
	private final String prev2Tag;

	public WDAND2TAGBFRRule(String from, String to, String prev2Tag, String word) {
		super(from, to);
		this.word = word;
		this.prev2Tag = prev2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag_2 = context.getToken(-2).getTag();
		String word0 = context.getToken(0).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prev2Tag != null ? prev2Tag.equals(tag_2) : tag_2 == null);
	}
}
