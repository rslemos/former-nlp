package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3OR4WDRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY1() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) {
				String word1 = context.getToken(1).getWord();
				return new NEXT1OR2OR3OR4WDRule<T1>(from, to, word1);
			}
			
		};
	}
	
	public static final <T1> RuleFactory<T1> FACTORY2() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) {
				String word2 = context.getToken(2).getWord();
				return new NEXT1OR2OR3OR4WDRule<T1>(from, to, word2);
			}
			
		};
	}
	
	public static final <T1> RuleFactory<T1> FACTORY3() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) {
				String word3 = context.getToken(3).getWord();
				return new NEXT1OR2OR3OR4WDRule<T1>(from, to, word3);
			}
			
		};
	}
	
	public static final <T1> RuleFactory<T1> FACTORY4() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) {
				String word4 = context.getToken(4).getWord();
				return new NEXT1OR2OR3OR4WDRule<T1>(from, to, word4);
			}
			
		};
	}
	
	private final String next1or2or3or4Word;

	public NEXT1OR2OR3OR4WDRule(T from, T to, String next1or2or3or4Word) {
		super(from, to);
		
		this.next1or2or3or4Word = next1or2or3or4Word;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();
		String word3 = context.getToken(3).getWord();
		String word4 = context.getToken(4).getWord();
		
		return next1or2or3or4Word != null 
		? (next1or2or3or4Word.equals(word1) | next1or2or3or4Word.equals(word2) | next1or2or3or4Word.equals(word3) | next1or2or3or4Word.equals(word4)) 
		: (word1 == null | word2 == null | word3 == null | word4 == null);
	}

	@SuppressWarnings("unchecked")
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
