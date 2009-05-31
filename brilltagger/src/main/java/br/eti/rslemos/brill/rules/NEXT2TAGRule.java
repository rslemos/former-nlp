package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT2TAGRule extends AbstractRule {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag2 = context.getToken(2).getTag();
			
			return new NEXT2TAGRule(from, to, tag2);
		}
		
	};

	private final String next2Tag;

	public NEXT2TAGRule(String from, String to, String next2Tag) {
		super(from, to);
		
		this.next2Tag = next2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag2 = context.getToken(2).getTag();
		
		return next2Tag != null ? next2Tag.equals(tag2) : tag2 == null;
	}
}
