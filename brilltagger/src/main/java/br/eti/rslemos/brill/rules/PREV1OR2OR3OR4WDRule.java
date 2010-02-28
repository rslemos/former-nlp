package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3OR4WDRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY1() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word_1 = context.getToken(-1).getWord();
				return new PREV1OR2OR3OR4WDRule<T1>(from, to, word_1);
			}
			
		};
	}
	
	public static final <T1> RuleFactory<T1> FACTORY2() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word_2 = context.getToken(-2).getWord();
				return new PREV1OR2OR3OR4WDRule<T1>(from, to, word_2);
			}
			
		};
	}
	
	public static final <T1> RuleFactory<T1> FACTORY3() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word_3 = context.getToken(-3).getWord();
				return new PREV1OR2OR3OR4WDRule<T1>(from, to, word_3);
			}
			
		};
	}
	
	public static final <T1> RuleFactory<T1> FACTORY4() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word_4 = context.getToken(-4).getWord();
				return new PREV1OR2OR3OR4WDRule<T1>(from, to, word_4);
			}
			
		};
	}
	
	private final String prev1or2or3or4Word;

	public PREV1OR2OR3OR4WDRule(T from, T to, String prev1or2or3or4Word) {
		super(from, to);
		
		this.prev1or2or3or4Word = prev1or2or3or4Word;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word_1 = context.getToken(-1).getWord();
		String word_2 = context.getToken(-2).getWord();
		String word_3 = context.getToken(-3).getWord();
		String word_4 = context.getToken(-4).getWord();
		
		return prev1or2or3or4Word != null 
		? (prev1or2or3or4Word.equals(word_1) | prev1or2or3or4Word.equals(word_2) | prev1or2or3or4Word.equals(word_3) | prev1or2or3or4Word.equals(word_4)) 
		: (word_1 == null | word_2 == null | word_3 == null | word_4 == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV1OR2OR3OR4WDRule other = (PREV1OR2OR3OR4WDRule) o;
		
		return prev1or2or3or4Word != null ? prev1or2or3or4Word.equals(other.prev1or2or3or4Word) : other.prev1or2or3or4Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 61;
		hashCode += prev1or2or3or4Word != null ? prev1or2or3or4Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev1or2or3or4Word;
	}
}
