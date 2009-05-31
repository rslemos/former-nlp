package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVTAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final AbstractRuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag_1 = context.getToken(-1).getTag();
			
			return new PREVTAGRule(from, to, tag_1);
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
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREVTAGRule other = (PREVTAGRule) o;
		
		return prevTag != null ? prevTag.equals(other.prevTag) : other.prevTag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 53;
		hashCode += prevTag != null ? prevTag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prevTag;
	}
}
