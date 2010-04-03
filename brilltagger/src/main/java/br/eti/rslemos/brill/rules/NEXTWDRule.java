package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.tagger.Tag;

public class NEXTWDRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new NEXTWDRuleFactory();
	}
	
	private final String nextWord;

	public NEXTWDRule(Tag from, Tag to, String nextWord) {
		super(from, to);
		
		this.nextWord = nextWord;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word1 = context.getToken(1).getWord();
		
		return nextWord != null ? nextWord.equals(word1) : word1 == null;
	}

	
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
