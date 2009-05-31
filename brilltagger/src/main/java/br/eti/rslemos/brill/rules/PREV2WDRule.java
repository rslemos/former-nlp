package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV2WDRule extends AbstractRule implements SerializableAsBrillText  {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word_2 = context.getToken(-2).getWord();
			
			return new PREV2WDRule(from, to, word_2);
		}
		
	};

	private final String prev2Word;

	public PREV2WDRule(String from, String to, String prev2Word) {
		super(from, to);
		this.prev2Word = prev2Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word_2 = context.getToken(-2).getWord();
		
		return prev2Word != null ? prev2Word.equals(word_2) : word_2 == null;
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV2WDRule other = (PREV2WDRule) o;
		
		return prev2Word != null ? prev2Word.equals(other.prev2Word) : other.prev2Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 47;
		hashCode += prev2Word != null ? prev2Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev2Word;
	}
}
