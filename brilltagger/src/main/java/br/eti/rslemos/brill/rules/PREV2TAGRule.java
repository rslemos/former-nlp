package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV2TAGRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				T1 tag_2 = context.getToken(-2).getTag();
				
				return new PREV2TAGRule<T1>(from, to, tag_2);
			}
			
		};
	}
	
	private final T prev2Tag;

	public PREV2TAGRule(T from, T to, T prev2Tag) {
		super(from, to);
		this.prev2Tag = prev2Tag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		T tag_2 = context.getToken(-2).getTag();
		
		return prev2Tag != null ? prev2Tag.equals(tag_2) : tag_2 == null;
	}
	
	@Override
	public boolean firingDependsOnTag(T tag) {
		return super.firingDependsOnTag(tag) || 
			(prev2Tag != null ? prev2Tag.equals(tag) : tag == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV2TAGRule other = (PREV2TAGRule) o;
		
		return prev2Tag != null ? prev2Tag.equals(other.prev2Tag) : other.prev2Tag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 43;
		hashCode += prev2Tag != null ? prev2Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev2Tag;
	}
}
