package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class LBIGRAMRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word0 = context.getToken(0).getWord();
				String word_1 = context.getToken(-1).getWord();
				
				return new LBIGRAMRule<T1>(from, to, word_1, word0);
			}
			
		};
	}
	
	private final String prevWord;
	private final String word;

	public LBIGRAMRule(T from, T to, String prevWord, String word) {
		super(from, to);
		this.prevWord = prevWord;
		this.word = word;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word0 = context.getToken(0).getWord();
		String word_1 = context.getToken(-1).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prevWord != null ? prevWord.equals(word_1) : word_1 == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		LBIGRAMRule other = (LBIGRAMRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
		(prevWord != null ? prevWord.equals(other.prevWord) : other.prevWord == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 7;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 5;
		hashCode += prevWord != null ? prevWord.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prevWord + " " + word;
	}
}
