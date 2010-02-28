package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVTAGRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				T1 tag_1 = context.getToken(-1).getTag();
				
				return new PREVTAGRule<T1>(from, to, tag_1);
			}
			
		};
	}
	
	private final T prevTag;

	public PREVTAGRule(T from, T to, T prevTag) {
		super(from, to);
		this.prevTag = prevTag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		T tag_1 = context.getToken(-1).getTag();
		
		return prevTag != null ? prevTag.equals(tag_1) : tag_1 == null;
	}
	
	@Override
	public boolean firingDependsOnTag(T tag) {
		return super.firingDependsOnTag(tag) || 
			(prevTag != null ? prevTag.equals(tag) : tag == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREVTAGRule other = (PREVTAGRule) o;
		
		return prevTag != null ? prevTag.equals(other.prevTag) : other.prevTag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 53;
		hashCode += prevTag != null ? prevTag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prevTag;
	}
}
