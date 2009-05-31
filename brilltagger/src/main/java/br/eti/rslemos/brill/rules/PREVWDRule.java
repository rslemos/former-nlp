package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVWDRule extends AbstractRule implements Rule {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word_1 = context.getToken(-1).getWord();
			
			return new PREVWDRule(from, to, word_1);
		}
		
	};

	private final String prevWord;

	public PREVWDRule(String from, String to, String prevWord) {
		super(from, to);
		this.prevWord = prevWord;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word_1 = context.getToken(-1).getWord();
		
		return prevWord != null ? prevWord.equals(word_1) : word_1 == null;
	}

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
}
