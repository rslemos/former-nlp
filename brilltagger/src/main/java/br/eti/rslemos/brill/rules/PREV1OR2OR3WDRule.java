package br.eti.rslemos.brill.rules;


import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class PREV1OR2OR3WDRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new PREV1OR2OR3WDRuleFactory<T1>();
	}
	
	private final String prev1or2or3Word;

	public PREV1OR2OR3WDRule(T from, T to, String prev1or2or3Word) {
		super(from, to);
		
		this.prev1or2or3Word = prev1or2or3Word;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word_1 = context.getToken(-1).getWord();
		String word_2 = context.getToken(-2).getWord();
		String word_3 = context.getToken(-3).getWord();
		
		return prev1or2or3Word != null 
		? (prev1or2or3Word.equals(word_1) | prev1or2or3Word.equals(word_2) | prev1or2or3Word.equals(word_3)) 
		: (word_1 == null | word_2 == null | word_3 == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV1OR2OR3WDRule other = (PREV1OR2OR3WDRule) o;
		
		return prev1or2or3Word != null ? prev1or2or3Word.equals(other.prev1or2or3Word) : other.prev1or2or3Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 61;
		hashCode += prev1or2or3Word != null ? prev1or2or3Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev1or2or3Word;
	}
}
