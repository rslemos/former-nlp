package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTWDRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractSingleRuleFactory<T1>() {
	
			public Rule<T1> createRule(T1 from, T1 to, Context<T1> context) {
				String word1 = context.getToken(1).getWord();
				
				return new NEXTWDRule<T1>(from, to, word1);
			}
			
		};
	}
	
	private final String nextWord;

	public NEXTWDRule(T from, T to, String nextWord) {
		super(from, to);
		
		this.nextWord = nextWord;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word1 = context.getToken(1).getWord();
		
		return nextWord != null ? nextWord.equals(word1) : word1 == null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXTWDRule other = (NEXTWDRule) o;
		
		return nextWord != null ? nextWord.equals(other.nextWord) : other.nextWord == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 29;
		hashCode += nextWord != null ? nextWord.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + nextWord;
	}
}
