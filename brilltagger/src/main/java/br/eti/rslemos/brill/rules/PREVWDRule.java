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

}
