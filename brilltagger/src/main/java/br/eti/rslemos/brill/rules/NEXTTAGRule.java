package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTTAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag1 = context.getToken(1).getTag();
			
			return new NEXTTAGRule(from, to, tag1);
		}
		
	};

	private final String nextTag;

	public NEXTTAGRule(String from, String to, String nextTag) {
		super(from, to);
		
		this.nextTag = nextTag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag1 = context.getToken(1).getTag();
		
		return nextTag != null ? nextTag.equals(tag1) : tag1 == null;
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXTTAGRule other = (NEXTTAGRule) o;
		
		return nextTag != null ? nextTag.equals(other.nextTag) : other.nextTag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 23;
		hashCode += nextTag != null ? nextTag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + nextTag;
	}
}
