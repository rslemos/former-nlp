package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3TAGRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Collection<Rule<T1>> create(T1 from, T1 to, Context<T1> context) {
				T1 tag_1 = context.getToken(-1).getTag();
				T1 tag_2 = context.getToken(-2).getTag();
				T1 tag_3 = context.getToken(-3).getTag();

				return Arrays.<Rule<T1>>asList(
						new PREV1OR2OR3TAGRule<T1>(from, to, tag_1),
						new PREV1OR2OR3TAGRule<T1>(from, to, tag_2),
						new PREV1OR2OR3TAGRule<T1>(from, to, tag_3)
				);
			}
			
		};
	}
	
	private final T prev1or2or3Tag;

	public PREV1OR2OR3TAGRule(T from, T to, T prev1or2or3Tag) {
		super(from, to);
		
		this.prev1or2or3Tag = prev1or2or3Tag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		T tag_1 = context.getToken(-1).getTag();
		T tag_2 = context.getToken(-2).getTag();
		T tag_3 = context.getToken(-3).getTag();
		
		return prev1or2or3Tag != null 
		? (prev1or2or3Tag.equals(tag_1) | prev1or2or3Tag.equals(tag_2) | prev1or2or3Tag.equals(tag_3)) 
		: (tag_1 == null | tag_2 == null | tag_3 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(T tag) {
		return super.firingDependsOnTag(tag) || 
			(prev1or2or3Tag != null ? prev1or2or3Tag.equals(tag) : tag == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV1OR2OR3TAGRule other = (PREV1OR2OR3TAGRule) o;
		
		return prev1or2or3Tag != null ? prev1or2or3Tag.equals(other.prev1or2or3Tag) : other.prev1or2or3Tag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 31;
		hashCode += prev1or2or3Tag != null ? prev1or2or3Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev1or2or3Tag;
	}
}
