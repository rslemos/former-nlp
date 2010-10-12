package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractBrillRule;
import br.eti.rslemos.brill.Context;

public class NEXT2TAGRule extends AbstractBrillRule {
	public static final RuleFactory FACTORY = NEXT2TAGRuleFactory.INSTANCE;
	
	private final Object next2Object;

	public NEXT2TAGRule(Object from, Object to, Object next2Object) {
		super(from, to);
		
		this.next2Object = next2Object;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Object tag2 = context.getToken(2).getTag();
		
		return next2Object != null ? next2Object.equals(tag2) : tag2 == null;
	}
	
	@Override
	public boolean firingDependsOnObject(Object tag) {
		return super.firingDependsOnObject(tag) || 
			(next2Object != null ? next2Object.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT2TAGRule other = (NEXT2TAGRule) o;
		
		return next2Object != null ? next2Object.equals(other.next2Object) : other.next2Object == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 17;
		hashCode += next2Object != null ? next2Object.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + next2Object;
	}
}
