package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class WDAND2BFRRule extends AbstractRule {
	public static final RuleFactory FACTORY = WDAND2BFRRuleFactory.INSTANCE;
	
	private final String prev2Word;
	private final String word;

	public WDAND2BFRRule(Object from, Object to, String prev2Word, String word) {
		super(from, to);
		this.prev2Word = prev2Word;
		this.word = word;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word_2 = context.getToken(-2).getWord();
		String word0 = context.getToken(0).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prev2Word != null ? prev2Word.equals(word_2) : word_2 == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		WDAND2BFRRule other = (WDAND2BFRRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(prev2Word != null ? prev2Word.equals(other.prev2Word) : other.prev2Word == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 19;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 5;
		hashCode += prev2Word != null ? prev2Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + prev2Word + " " + word;
	}
}
