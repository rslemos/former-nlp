package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class CURWDRule extends AbstractRule {
	public static final AbstractRuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word0 = context.getToken(0).getWord();
			
			return new CURWDRule(from, to, word0);
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
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		CURWDRule other = (CURWDRule) o;
		
		return word != null ? word.equals(other.word) : other.word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 5;
		hashCode += word != null ? word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toString() {
		return super.toString() + " " + word;
	}
}
