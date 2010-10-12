package br.eti.rslemos.brill.rules;


import br.eti.rslemos.brill.AbstractBrillRule;
import br.eti.rslemos.brill.Context;

public class NEXT1OR2WDRule extends AbstractBrillRule {
	public static final RuleFactory FACTORY = NEXT1OR2WDRuleFactory.INSTANCE;
	
	private final String next1or2Word;

	public NEXT1OR2WDRule(Object from, Object to, String next1or2Word) {
		super(from, to);
		
		this.next1or2Word = next1or2Word;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();
		
		return next1or2Word != null 
		? (next1or2Word.equals(word1) | next1or2Word.equals(word2)) 
		: (word1 == null | word2 == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT1OR2WDRule other = (NEXT1OR2WDRule) o;
		
		return next1or2Word != null ? next1or2Word.equals(other.next1or2Word) : other.next1or2Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 13;
		hashCode += next1or2Word != null ? next1or2Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + next1or2Word;
	}
}
