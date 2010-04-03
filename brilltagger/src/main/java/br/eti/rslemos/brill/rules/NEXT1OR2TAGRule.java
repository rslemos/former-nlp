package br.eti.rslemos.brill.rules;


import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class NEXT1OR2TAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new NEXT1OR2TAGRuleFactory();
	}
	
	private final Object next1or2Object;

	public NEXT1OR2TAGRule(Object from, Object to, Object next1or2Object) {
		super(from, to);
		
		this.next1or2Object = next1or2Object;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Object tag1 = context.getToken(1).getTag();
		Object tag2 = context.getToken(2).getTag();
		
		return next1or2Object != null 
		? (next1or2Object.equals(tag1) | next1or2Object.equals(tag2)) 
		: (tag1 == null | tag2 == null);
	}
	
	@Override
	public boolean firingDependsOnObject(Object tag) {
		return super.firingDependsOnObject(tag) || 
			(next1or2Object != null ? next1or2Object.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT1OR2TAGRule other = (NEXT1OR2TAGRule) o;
		
		return next1or2Object != null ? next1or2Object.equals(other.next1or2Object) : other.next1or2Object == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 11;
		hashCode += next1or2Object != null ? next1or2Object.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + next1or2Object;
	}
}
