package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2WDRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY1() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word1 = context.getToken(1).getWord();
				return new NEXT1OR2WDRule<T1>(from, to, word1);
			}
			
		};
	}
	
	public static final <T1> RuleFactory<T1> FACTORY2() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word2 = context.getToken(2).getWord();
				return new NEXT1OR2WDRule<T1>(from, to, word2);
			}
			
		};
	}
	
	private final String next1or2Word;

	public NEXT1OR2WDRule(T from, T to, String next1or2Word) {
		super(from, to);
		
		this.next1or2Word = next1or2Word;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();
		
		return next1or2Word != null 
		? (next1or2Word.equals(word1) | next1or2Word.equals(word2)) 
		: (word1 == null | word2 == null);
	}

	@SuppressWarnings("unchecked")
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
	public String toBrillText() {
		return super.toBrillText() + " " + next1or2Word;
	}
}
