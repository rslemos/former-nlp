package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTTAGRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {

			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				T1 tag1 = context.getToken(1).getTag();
				
				return new NEXTTAGRule<T1>(from, to, tag1);
			}
			
		};
	}
	
	private final T nextTag;

	public NEXTTAGRule(T from, T to, T nextTag) {
		super(from, to);
		
		this.nextTag = nextTag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		T tag1 = context.getToken(1).getTag();
		
		return nextTag != null ? nextTag.equals(tag1) : tag1 == null;
	}
	
	@Override
	public boolean firingDependsOnTag(T tag) {
		return super.firingDependsOnTag(tag) || 
			(nextTag != null ? nextTag.equals(tag) : tag == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXTTAGRule other = (NEXTTAGRule) o;
		
		return nextTag != null ? nextTag.equals(other.nextTag) : other.nextTag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 23;
		hashCode += nextTag != null ? nextTag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + nextTag;
	}
}
