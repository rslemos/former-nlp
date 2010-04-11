package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class RBIGRAMRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new RBIGRAMRuleFactory();
	}
	
	private final String word;
	private final String nextWord;

	public RBIGRAMRule(Object from, Object to, String word, String nextWord) {
		super(from, to);
		this.word = word;
		this.nextWord = nextWord;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String word1 = context.getToken(1).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(nextWord != null ? nextWord.equals(word1) : word1 == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		RBIGRAMRule other = (RBIGRAMRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(nextWord != null ? nextWord.equals(other.nextWord) : other.nextWord == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 13;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 5;
		hashCode += nextWord != null ? nextWord.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + word + " " + nextWord;
	}
}
