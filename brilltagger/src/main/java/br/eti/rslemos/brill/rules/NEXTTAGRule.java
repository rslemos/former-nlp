package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class NEXTTAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new NEXTTAGRuleFactory();
	}
	
	private final Object nextObject;

	public NEXTTAGRule(Object from, Object to, Object nextObject) {
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
	public boolean firingDependsOnObject(Object tag) {
		return super.firingDependsOnObject(tag) || 
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
	public String toBrillText() {
		return super.toBrillText() + " " + nextObject;
	}
}
