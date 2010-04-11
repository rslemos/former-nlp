package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class PREV2TAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new PREV2TAGRuleFactory();
	}
	
	private final Object prev2Object;

	public PREV2TAGRule(Object from, Object to, Object prev2Object) {
		super(from, to);
		this.prev2Object = prev2Object;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Object tag_2 = context.getToken(-2).getTag();
		
		return prev2Object != null ? prev2Object.equals(tag_2) : tag_2 == null;
	}
	
	@Override
	public boolean firingDependsOnObject(Object tag) {
		return super.firingDependsOnObject(tag) || 
			(prev2Object != null ? prev2Object.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV2TAGRule other = (PREV2TAGRule) o;
		
		return prev2Object != null ? prev2Object.equals(other.prev2Object) : other.prev2Object == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 43;
		hashCode += prev2Object != null ? prev2Object.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev2Object;
	}
}
