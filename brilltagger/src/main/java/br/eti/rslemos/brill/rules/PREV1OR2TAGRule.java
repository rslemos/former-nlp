package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2TAGRule extends AbstractRule {
	public static final RuleFactory FACTORY1 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag_1 = context.getToken(-1).getTag();
			return new PREV1OR2TAGRule(from, to, tag_1);
		}
		
	};

	public static final RuleFactory FACTORY2 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag_2 = context.getToken(-2).getTag();
			return new PREV1OR2TAGRule(from, to, tag_2);
		}
		
	};

	private final String prev1or2Tag;

	public PREV1OR2TAGRule(String from, String to, String prev1or2Tag) {
		super(from, to);
		
		this.prev1or2Tag = prev1or2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag_1 = context.getToken(-1).getTag();
		String tag_2 = context.getToken(-2).getTag();
		
		return prev1or2Tag != null 
		? (prev1or2Tag.equals(tag_1) | prev1or2Tag.equals(tag_2)) 
		: (tag_1 == null | tag_2 == null);
	}
}
