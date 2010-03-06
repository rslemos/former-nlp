package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT2TAGRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) {
				T1 tag2 = context.getToken(2).getTag();
				
				return new NEXT2TAGRule<T1>(from, to, tag2);
			}
			
		};
	}
	
	private final T next2Tag;

	public NEXT2TAGRule(T from, T to, T next2Tag) {
		super(from, to);
		
		this.next2Tag = next2Tag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		T tag2 = context.getToken(2).getTag();
		
		return next2Tag != null ? next2Tag.equals(tag2) : tag2 == null;
	}
	
	@Override
	public boolean firingDependsOnTag(T tag) {
		return super.firingDependsOnTag(tag) || 
			(next2Tag != null ? next2Tag.equals(tag) : tag == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT2TAGRule other = (NEXT2TAGRule) o;
		
		return next2Tag != null ? next2Tag.equals(other.next2Tag) : other.next2Tag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 17;
		hashCode += next2Tag != null ? next2Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + next2Tag;
	}
}
