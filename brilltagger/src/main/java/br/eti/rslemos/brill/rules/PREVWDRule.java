package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class PREVWDRule extends AbstractRule {
	public static final RuleFactory FACTORY = PREVWDRuleFactory.INSTANCE;
	
	private final String prevWord;

	public PREVWDRule(Object from, Object to, String prevWord) {
		super(from, to);
		this.prevWord = prevWord;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word_1 = context.getToken(-1).getWord();
		
		return prevWord != null ? prevWord.equals(word_1) : word_1 == null;
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREVWDRule other = (PREVWDRule) o;
		
		return prevWord != null ? prevWord.equals(other.prevWord) : other.prevWord == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 59;
		hashCode += prevWord != null ? prevWord.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + prevWord;
	}
}
