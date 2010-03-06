package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVBIGRAMRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractSingleRuleFactory<T1>() {
	
			public Rule<T1> createRule(T1 from, T1 to, Context<T1> context) {
				String word_2 = context.getToken(-2).getWord();
				String word_1 = context.getToken(-1).getWord();
				
				return new PREVBIGRAMRule<T1>(from, to, word_2, word_1);
			}
			
		};
	}
	
	private final String prev2Word;
	private final String prev1Word;

	public PREVBIGRAMRule(T from, T to, String prev2Word, String prev1Word) {
		super(from, to);
		this.prev2Word = prev2Word;
		this.prev1Word = prev1Word;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word_2 = context.getToken(-2).getWord();
		String word_1 = context.getToken(-1).getWord();
		
		return (prev2Word != null ? prev2Word.equals(word_2) : word_2 == null) &&
			(prev1Word != null ? prev1Word.equals(word_1) : word_1 == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREVBIGRAMRule other = (PREVBIGRAMRule) o;
		
		return (prev2Word != null ? prev2Word.equals(other.prev2Word) : other.prev2Word == null) &&
			(prev1Word != null ? prev1Word.equals(other.prev1Word) : other.prev1Word == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 11;
		hashCode += prev2Word != null ? prev2Word.hashCode() : 0;
		hashCode *= 7;
		hashCode += prev1Word != null ? prev1Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev2Word + " " + prev1Word;
	}
}
