package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;

public class PREVTAGRule extends AbstractRule implements Rule {

	public static final RuleFactory FACTORY = new RuleFactory() {

		public Rule create(Context context, Token target) throws RuleCreationException {
			String tag0 = context.getToken(0).getTag();

			String tag_1 = context.getToken(-1).getTag();
			
			return new PREVTAGRule(tag0, target.getTag(), tag_1);
		}
		
	};
	
	private final String prevTag;

	public PREVTAGRule(String from, String to, String prevTag) {
		super(from, to);
		this.prevTag = prevTag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag_1 = context.getToken(-1).getTag();
		
		return prevTag != null ? prevTag.equals(tag_1) : tag_1 == null;
	}


	@Override
	public String toString() {
		return super.toString() + " " + prevTag;
	}
}
