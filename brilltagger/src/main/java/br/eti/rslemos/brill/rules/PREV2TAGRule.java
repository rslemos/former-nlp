package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV2TAGRule extends AbstractRule implements Rule {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag_2 = context.getToken(-2).getTag();
			
			return new PREV2TAGRule(from, to, tag_2);
		}
		
	};

	private final String prev2Tag;

	public PREV2TAGRule(String from, String to, String prev2Tag) {
		super(from, to);
		this.prev2Tag = prev2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag_2 = context.getToken(-2).getTag();
		
		return prev2Tag != null ? prev2Tag.equals(tag_2) : tag_2 == null;
	}

}
