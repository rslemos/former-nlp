package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class NEXTTAGRule extends AbstractRule {
	public static NEXTTAGRule createRule(Object from, Object to,
			Object nextObject) {
		return new NEXTTAGRule(from, to, nextObject);
	}

	private final Object nextObject;

	private NEXTTAGRule(Object from, Object to, Object nextObject) {
		super(from, to);
		
		this.nextObject = nextObject;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Object tag1 = context.getToken(1).getTag();
		
		return nextObject != null ? nextObject.equals(tag1) : tag1 == null;
	}
	
	@Override
	public boolean testsTag(Object tag) {
		return super.testsTag(tag) || 
			(nextObject != null ? nextObject.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXTTAGRule other = (NEXTTAGRule) o;
		
		return nextObject != null ? nextObject.equals(other.nextObject) : other.nextObject == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 23;
		hashCode += nextObject != null ? nextObject.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + nextObject;
	}
}
