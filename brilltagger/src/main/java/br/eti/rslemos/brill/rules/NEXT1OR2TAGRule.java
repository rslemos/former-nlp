package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2TAGRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Collection<Rule<T1>> create(T1 from, T1 to, Context<T1> context) {
				T1 tag1 = context.getToken(1).getTag();
				T1 tag2 = context.getToken(2).getTag();
				
				return Arrays.<Rule<T1>>asList(
						new NEXT1OR2TAGRule<T1>(from, to, tag1),
						new NEXT1OR2TAGRule<T1>(from, to, tag2)
				);
			}
			
		};
	}
	
	private final T next1or2Tag;

	public NEXT1OR2TAGRule(T from, T to, T next1or2Tag) {
		super(from, to);
		
		this.next1or2Tag = next1or2Tag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		T tag1 = context.getToken(1).getTag();
		T tag2 = context.getToken(2).getTag();
		
		return next1or2Tag != null 
		? (next1or2Tag.equals(tag1) | next1or2Tag.equals(tag2)) 
		: (tag1 == null | tag2 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(T tag) {
		return super.firingDependsOnTag(tag) || 
			(next1or2Tag != null ? next1or2Tag.equals(tag) : tag == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT1OR2TAGRule other = (NEXT1OR2TAGRule) o;
		
		return next1or2Tag != null ? next1or2Tag.equals(other.next1or2Tag) : other.next1or2Tag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 11;
		hashCode += next1or2Tag != null ? next1or2Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + next1or2Tag;
	}
}
