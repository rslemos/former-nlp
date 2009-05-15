package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDPREVTAGRule extends AbstractRule implements Rule {

	private final String word;
	private final String prevTag;

	public WDPREVTAGRule(String from, String to, String word, String prevTag) {
		super(from, to);
		this.word = word;
		this.prevTag = prevTag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String tag_1 = context.getToken(-1).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prevTag != null ? prevTag.equals(tag_1) : tag_1 == null);
	}
}
