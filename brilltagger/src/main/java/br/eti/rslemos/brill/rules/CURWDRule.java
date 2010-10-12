package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractBrillRule;
import br.eti.rslemos.brill.Context;

public class CURWDRule extends AbstractBrillRule {
	public static final RuleFactory FACTORY = CURWDRuleFactory.INSTANCE;
	
	private final String word;

	public CURWDRule(Object from, Object to, String word) {
		super(from, to);
		this.word = word;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		
		return word != null ? word.equals(word0) : word0 == null;
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		CURWDRule other = (CURWDRule) o;
		
		return word != null ? word.equals(other.word) : other.word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 5;
		hashCode += word != null ? word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + word;
	}
}
