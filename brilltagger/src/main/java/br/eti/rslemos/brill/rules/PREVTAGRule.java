package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class PREVTAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new PREVTAGRuleFactory();
	}
	
	private final Object prevObject;

	public PREVTAGRule(Object from, Object to, Object prevObject) {
		super(from, to);
		this.prevObject = prevObject;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Object tag_1 = context.getToken(-1).getTag();
		
		return prevObject != null ? prevObject.equals(tag_1) : tag_1 == null;
	}
	
	@Override
	public boolean firingDependsOnObject(Object tag) {
		return super.firingDependsOnObject(tag) || 
			(prevObject != null ? prevObject.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREVTAGRule other = (PREVTAGRule) o;
		
		return prevObject != null ? prevObject.equals(other.prevObject) : other.prevObject == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 53;
		hashCode += prevObject != null ? prevObject.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prevObject;
	}
}
