package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2WDRule extends AbstractRule {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			throw new RuleCreationException("Oops. Modelo de fábrica não está preparada para regras OU");
		}
		
	};

	private final String next1or2Word;

	public NEXT1OR2WDRule(String from, String to, String next1or2Word) {
		super(from, to);
		
		this.next1or2Word = next1or2Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();
		
		return next1or2Word != null 
		? (next1or2Word.equals(word1) | next1or2Word.equals(word2)) 
		: (word1 == null | word2 == null);
	}
}
