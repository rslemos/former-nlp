package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class SURROUNDTAGRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new SURROUNDTAGRuleFactory<T1>();
	}
	
	private final T prev1Tag;
	private final T next1Tag;

	public SURROUNDTAGRule(T from, T to, T prev1Tag, T next1Tag) {
		super(from, to);
		this.prev1Tag = prev1Tag;
		this.next1Tag = next1Tag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		T tag_1 = context.getToken(-1).getTag();
		T tag1 = context.getToken(1).getTag();
		
		return (prev1Tag != null ? prev1Tag.equals(tag_1) : tag_1 == null) &&
			(next1Tag != null ? next1Tag.equals(tag1) : tag1 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(T tag) {
		return super.firingDependsOnTag(tag) || 
			(prev1Tag != null ? prev1Tag.equals(tag) : tag == null) ||
			(next1Tag != null ? next1Tag.equals(tag) : tag == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		SURROUNDTAGRule other = (SURROUNDTAGRule) o;
		
		return (prev1Tag != null ? prev1Tag.equals(other.prev1Tag) : other.prev1Tag == null) &&
			(next1Tag != null ? next1Tag.equals(other.next1Tag) : other.next1Tag == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 13;
		hashCode += prev1Tag != null ? prev1Tag.hashCode() : 0;
		hashCode *= 7;
		hashCode += next1Tag != null ? next1Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev1Tag + " " + next1Tag;
	}
}
