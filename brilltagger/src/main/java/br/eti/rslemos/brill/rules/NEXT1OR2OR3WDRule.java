package br.eti.rslemos.brill.rules;


import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class NEXT1OR2OR3WDRule extends AbstractRule {
	public static NEXT1OR2OR3WDRule createRule(Object from, Object to,
			String next1or2or3Word) {
		return new NEXT1OR2OR3WDRule(from, to, next1or2or3Word);
	}

	private final String next1or2or3Word;

	private NEXT1OR2OR3WDRule(Object from, Object to, String next1or2or3Word) {
		super(from, to);
		
		this.next1or2or3Word = next1or2or3Word;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();
		String word3 = context.getToken(3).getWord();
		
		return next1or2or3Word != null 
		? (next1or2or3Word.equals(word1) | next1or2or3Word.equals(word2) | next1or2or3Word.equals(word3)) 
		: (word1 == null | word2 == null | word3 == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT1OR2OR3WDRule other = (NEXT1OR2OR3WDRule) o;
		
		return next1or2or3Word != null ? next1or2or3Word.equals(other.next1or2or3Word) : other.next1or2or3Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 67;
		hashCode += next1or2or3Word != null ? next1or2or3Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + next1or2or3Word;
	}
}
