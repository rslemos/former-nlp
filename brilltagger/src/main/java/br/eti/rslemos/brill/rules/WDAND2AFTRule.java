package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2AFTRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) {
				String word0 = context.getToken(0).getWord();
				String word2 = context.getToken(2).getWord();
				
				return new WDAND2AFTRule<T1>(from, to, word0, word2);
			}
			
		};
	}
	
	private final String word;
	private final String next2Word;

	public WDAND2AFTRule(T from, T to, String word, String next2Word) {
		super(from, to);
		this.word = word;
		this.next2Word = next2Word;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word0 = context.getToken(0).getWord();
		String word2 = context.getToken(2).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next2Word != null ? next2Word.equals(word2) : word2 == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		WDAND2AFTRule other = (WDAND2AFTRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(next2Word != null ? next2Word.equals(other.next2Word) : other.next2Word == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 13;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 11;
		hashCode += next2Word != null ? next2Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + word + " " + next2Word;
	}
}
