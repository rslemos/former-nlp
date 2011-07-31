package br.eti.rslemos.brill.rules;


import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class PREV1OR2TAGRule extends AbstractRule {
	public static final RuleFactory FACTORY = PREV1OR2TAGRuleFactory.INSTANCE;
	
	private final Object prev1or2Object;

	public PREV1OR2TAGRule(Object from, Object to, Object prev1or2Object) {
		super(from, to);
		
		this.prev1or2Object = prev1or2Object;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Object tag_1 = context.getToken(-1).getTag();
		Object tag_2 = context.getToken(-2).getTag();
		
		return prev1or2Object != null 
		? (prev1or2Object.equals(tag_1) | prev1or2Object.equals(tag_2)) 
		: (tag_1 == null | tag_2 == null);
	}
	
	@Override
	public boolean firingDependsOnObject(Object tag) {
		return super.firingDependsOnObject(tag) || 
			(prev1or2Object != null ? prev1or2Object.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV1OR2TAGRule other = (PREV1OR2TAGRule) o;
		
		return prev1or2Object != null ? prev1or2Object.equals(other.prev1or2Object) : other.prev1or2Object == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 37;
		hashCode += prev1or2Object != null ? prev1or2Object.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + prev1or2Object;
	}
}
