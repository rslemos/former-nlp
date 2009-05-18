package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;

public class CURWDRule extends AbstractRule {
	public static final RuleFactory FACTORY = new RuleFactory() {

		public Rule create(Context context, Token target) throws RuleCreationException {
			String tag0 = context.getToken(0).getTag();
			
			String word0 = context.getToken(0).getWord();
			
			return new CURWDRule(tag0, target.getTag(), word0);
		}
		
	};
	
	private final String word;

	public CURWDRule(String from, String to, String word) {
		super(from, to);
		this.word = word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		
		return word != null ? word.equals(word0) : word0 == null;
	}

	@Override
	public String toString() {
		return super.toString() + " " + word;
	}
}
