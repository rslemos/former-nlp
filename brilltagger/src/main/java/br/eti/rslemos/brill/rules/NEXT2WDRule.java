package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT2WDRule extends AbstractRule {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word2 = context.getToken(2).getWord();
			
			return new NEXT2WDRule(from, to, word2);
		}
		
	};

	private final String next2Word;

	public NEXT2WDRule(String from, String to, String next2Word) {
		super(from, to);
		
		this.next2Word = next2Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word2 = context.getToken(2).getWord();
		
		return next2Word != null ? next2Word.equals(word2) : word2 == null;
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT2WDRule other = (NEXT2WDRule) o;
		
		return next2Word != null ? next2Word.equals(other.next2Word) : other.next2Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 19;
		hashCode += next2Word != null ? next2Word.hashCode() : 0;
		
		return hashCode;
	}
}
