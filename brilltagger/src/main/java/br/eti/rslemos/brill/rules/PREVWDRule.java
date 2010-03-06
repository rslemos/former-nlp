package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVWDRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) {
				String word_1 = context.getToken(-1).getWord();
				
				return new PREVWDRule<T1>(from, to, word_1);
			}
			
		};
	}
	
	private final String prevWord;

	public PREVWDRule(T from, T to, String prevWord) {
		super(from, to);
		this.prevWord = prevWord;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word_1 = context.getToken(-1).getWord();
		
		return prevWord != null ? prevWord.equals(word_1) : word_1 == null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREVWDRule other = (PREVWDRule) o;
		
		return prevWord != null ? prevWord.equals(other.prevWord) : other.prevWord == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 59;
		hashCode += prevWord != null ? prevWord.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prevWord;
	}
}
