package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDNEXTTAGRule extends AbstractRule implements Rule {

	private final String word;
	private final String next1Tag;

	public WDNEXTTAGRule(String from, String to, String word, String next1Tag) {
		super(from, to);
		this.word = word;
		this.next1Tag = next1Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String tag1 = context.getToken(1).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next1Tag != null ? next1Tag.equals(tag1) : tag1 == null);
	}
}
