package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2AFTRule extends AbstractRule implements Rule {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word0 = context.getToken(0).getWord();
			String word2 = context.getToken(2).getWord();
			
			return new WDAND2AFTRule(from, to, word0, word2);
		}
		
	};

	private final String word;
	private final String next2Word;

	public WDAND2AFTRule(String from, String to, String word, String next2Word) {
		super(from, to);
		this.word = word;
		this.next2Word = next2Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String word2 = context.getToken(2).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next2Word != null ? next2Word.equals(word2) : word2 == null);
	}
}
