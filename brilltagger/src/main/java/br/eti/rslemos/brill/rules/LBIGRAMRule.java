package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class LBIGRAMRule extends AbstractRule implements SerializableAsBrillText  {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word0 = context.getToken(0).getWord();
			String word_1 = context.getToken(-1).getWord();
			
			return new LBIGRAMRule(from, to, word_1, word0);
		}
		
	};

	private final String prevWord;
	private final String word;

	public LBIGRAMRule(String from, String to, String prevWord, String word) {
		super(from, to);
		this.prevWord = prevWord;
		this.word = word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String word_1 = context.getToken(-1).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prevWord != null ? prevWord.equals(word_1) : word_1 == null);
	}

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
