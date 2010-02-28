package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2WDRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY1() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word_1 = context.getToken(-1).getWord();
				return new PREV1OR2WDRule<T1>(from, to, word_1);
			}
			
		};
	}
	
	public static final <T1> RuleFactory<T1> FACTORY2() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word_2 = context.getToken(-2).getWord();
				return new PREV1OR2WDRule<T1>(from, to, word_2);
			}
			
		};
	}
	
	private final String prev1or2Word;

	public PREV1OR2WDRule(T from, T to, String prev1or2Word) {
		super(from, to);
		
		this.prev1or2Word = prev1or2Word;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word_1 = context.getToken(-1).getWord();
		String word_2 = context.getToken(-2).getWord();
		
		return prev1or2Word != null 
		? (prev1or2Word.equals(word_1) | prev1or2Word.equals(word_2)) 
		: (word_1 == null | word_2 == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV1OR2WDRule other = (PREV1OR2WDRule) o;
		
		return prev1or2Word != null ? prev1or2Word.equals(other.prev1or2Word) : other.prev1or2Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 41;
		hashCode += prev1or2Word != null ? prev1or2Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev1or2Word;
	}
}
