package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2WDRule extends AbstractRule {
	public static final RuleFactory FACTORY1 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word_1 = context.getToken(-1).getWord();
			return new PREV1OR2WDRule(from, to, word_1);
		}
		
	};

	public static final RuleFactory FACTORY2 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word_2 = context.getToken(-2).getWord();
			return new PREV1OR2WDRule(from, to, word_2);
		}
		
	};

	private final String prev1or2Word;

	public PREV1OR2WDRule(String from, String to, String prev1or2Word) {
		super(from, to);
		
		this.prev1or2Word = prev1or2Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word_1 = context.getToken(-1).getWord();
		String word_2 = context.getToken(-2).getWord();
		
		return prev1or2Word != null 
		? (prev1or2Word.equals(word_1) | prev1or2Word.equals(word_2)) 
		: (word_1 == null | word_2 == null);
	}
}
