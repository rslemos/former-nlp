package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class PREV2WDRule extends AbstractRule {
	public static PREV2WDRule createRule(Object from, Object to,
			String prev2Word) {
		return new PREV2WDRule(from, to, prev2Word);
	}

	private final String prev2Word;

	private PREV2WDRule(Object from, Object to, String prev2Word) {
		super(from, to);
		this.prev2Word = prev2Word;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word_2 = context.getToken(-2).getWord();
		
		return prev2Word != null ? prev2Word.equals(word_2) : word_2 == null;
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV2WDRule other = (PREV2WDRule) o;
		
		return prev2Word != null ? prev2Word.equals(other.prev2Word) : other.prev2Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 47;
		hashCode += prev2Word != null ? prev2Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + prev2Word;
	}
}
