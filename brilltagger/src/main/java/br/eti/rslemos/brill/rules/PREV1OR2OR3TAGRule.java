package br.eti.rslemos.brill.rules;


import br.eti.rslemos.brill.AbstractBrillRule;
import br.eti.rslemos.brill.Context;

public class PREV1OR2OR3TAGRule extends AbstractBrillRule {
	public static final RuleFactory FACTORY = PREV1OR2OR3TAGRuleFactory.INSTANCE;
	
	private final Object prev1or2or3Object;

	public PREV1OR2OR3TAGRule(Object from, Object to, Object prev1or2or3Object) {
		super(from, to);
		
		this.prev1or2or3Object = prev1or2or3Object;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Object tag_1 = context.getToken(-1).getTag();
		Object tag_2 = context.getToken(-2).getTag();
		Object tag_3 = context.getToken(-3).getTag();
		
		return prev1or2or3Object != null 
		? (prev1or2or3Object.equals(tag_1) | prev1or2or3Object.equals(tag_2) | prev1or2or3Object.equals(tag_3)) 
		: (tag_1 == null | tag_2 == null | tag_3 == null);
	}
	
	@Override
	public boolean firingDependsOnObject(Object tag) {
		return super.firingDependsOnObject(tag) || 
			(prev1or2or3Object != null ? prev1or2or3Object.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV1OR2OR3TAGRule other = (PREV1OR2OR3TAGRule) o;
		
		return prev1or2or3Object != null ? prev1or2or3Object.equals(other.prev1or2or3Object) : other.prev1or2or3Object == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 31;
		hashCode += prev1or2or3Object != null ? prev1or2or3Object.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + prev1or2or3Object;
	}
}
