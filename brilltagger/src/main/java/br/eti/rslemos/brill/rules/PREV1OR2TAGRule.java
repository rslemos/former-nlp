package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2TAGRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Collection<Rule<T1>> create(T1 from, T1 to, Context<T1> context) {
				T1 tag_1 = context.getToken(-1).getTag();
				T1 tag_2 = context.getToken(-2).getTag();
				
				return Arrays.<Rule<T1>>asList(
						new PREV1OR2TAGRule<T1>(from, to, tag_1),
						new PREV1OR2TAGRule<T1>(from, to, tag_2)
				);
			}
			
		};
	}
	
	private final T prev1or2Tag;

	public PREV1OR2TAGRule(T from, T to, T prev1or2Tag) {
		super(from, to);
		
		this.prev1or2Tag = prev1or2Tag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		T tag_1 = context.getToken(-1).getTag();
		T tag_2 = context.getToken(-2).getTag();
		
		return prev1or2Tag != null 
		? (prev1or2Tag.equals(tag_1) | prev1or2Tag.equals(tag_2)) 
		: (tag_1 == null | tag_2 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(T tag) {
		return super.firingDependsOnTag(tag) || 
			(prev1or2Tag != null ? prev1or2Tag.equals(tag) : tag == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV1OR2TAGRule other = (PREV1OR2TAGRule) o;
		
		return prev1or2Tag != null ? prev1or2Tag.equals(other.prev1or2Tag) : other.prev1or2Tag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 37;
		hashCode += prev1or2Tag != null ? prev1or2Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev1or2Tag;
	}
}
