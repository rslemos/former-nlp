package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class WDPREVTAGRule extends AbstractRule {
	public static final AbstractRuleFactory FACTORY = WDPREVTAGRuleFactory.INSTANCE;
	
	private final String word;
	private final Object prevObject;

	public WDPREVTAGRule(Object from, Object to, Object prevObject, String word) {
		super(from, to);
		this.word = word;
		this.prevObject = prevObject;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		Object tag_1 = context.getToken(-1).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prevObject != null ? prevObject.equals(tag_1) : tag_1 == null);
	}
	
	@Override
	public boolean testsTag(Object tag) {
		return super.testsTag(tag) || 
			(prevObject != null ? prevObject.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		WDPREVTAGRule other = (WDPREVTAGRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(prevObject != null ? prevObject.equals(other.prevObject) : other.prevObject == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 23;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 5;
		hashCode += prevObject != null ? prevObject.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + prevObject + " " + word;
	}
}
