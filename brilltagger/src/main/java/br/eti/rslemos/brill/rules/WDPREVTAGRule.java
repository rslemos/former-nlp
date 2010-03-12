package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class WDPREVTAGRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> AbstractRuleFactory<T1> FACTORY() {
		return new WDPREVTAGRuleFactory<T1>();
	}
	
	private final String word;
	private final T prevTag;

	public WDPREVTAGRule(T from, T to, T prevTag, String word) {
		super(from, to);
		this.word = word;
		this.prevTag = prevTag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word0 = context.getToken(0).getWord();
		T tag_1 = context.getToken(-1).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prevTag != null ? prevTag.equals(tag_1) : tag_1 == null);
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
		
		WDPREVTAGRule other = (WDPREVTAGRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(prevTag != null ? prevTag.equals(other.prevTag) : other.prevTag == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 23;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 5;
		hashCode += prevTag != null ? prevTag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prevTag + " " + word;
	}
}
