package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT2WDRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractSingleRuleFactory<T1>() {
	
			public Rule<T1> createRule(T1 from, T1 to, Context<T1> context) {
				String word2 = context.getToken(2).getWord();
				
				return new NEXT2WDRule<T1>(from, to, word2);
			}
			
		};
	}
	
	private final String next2Word;

	public NEXT2WDRule(T from, T to, String next2Word) {
		super(from, to);
		
		this.next2Word = next2Word;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word2 = context.getToken(2).getWord();
		
		return next2Word != null ? next2Word.equals(word2) : word2 == null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT2WDRule other = (NEXT2WDRule) o;
		
		return next2Word != null ? next2Word.equals(other.next2Word) : other.next2Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 19;
		hashCode += next2Word != null ? next2Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + next2Word;
	}
}
