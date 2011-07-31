package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class WDAND2AFTRule extends AbstractRule {
	public static final RuleFactory FACTORY = WDAND2AFTRuleFactory.INSTANCE;
	
	private final String word;
	private final String next2Word;

	public WDAND2AFTRule(Object from, Object to, String word, String next2Word) {
		super(from, to);
		this.word = word;
		this.next2Word = next2Word;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String word2 = context.getToken(2).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next2Word != null ? next2Word.equals(word2) : word2 == null);
	}

	
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
	public String toBrillString() {
		return super.toBrillString() + " " + word + " " + next2Word;
	}
}
