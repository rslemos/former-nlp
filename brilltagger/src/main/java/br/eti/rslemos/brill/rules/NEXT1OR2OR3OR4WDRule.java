package br.eti.rslemos.brill.rules;


import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class NEXT1OR2OR3OR4WDRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new NEXT1OR2OR3OR4WDRuleFactory();
	}
	
	private final String next1or2or3or4Word;

	public NEXT1OR2OR3OR4WDRule(Object from, Object to, String next1or2or3or4Word) {
		super(from, to);
		
		this.next1or2or3or4Word = next1or2or3or4Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();
		String word3 = context.getToken(3).getWord();
		String word4 = context.getToken(4).getWord();
		
		return next1or2or3or4Word != null 
		? (next1or2or3or4Word.equals(word1) | next1or2or3or4Word.equals(word2) | next1or2or3or4Word.equals(word3) | next1or2or3or4Word.equals(word4)) 
		: (word1 == null | word2 == null | word3 == null | word4 == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT1OR2OR3OR4WDRule other = (NEXT1OR2OR3OR4WDRule) o;
		
		return next1or2or3or4Word != null ? next1or2or3or4Word.equals(other.next1or2or3or4Word) : other.next1or2or3or4Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 67;
		hashCode += next1or2or3or4Word != null ? next1or2or3or4Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + next1or2or3or4Word;
	}
}
