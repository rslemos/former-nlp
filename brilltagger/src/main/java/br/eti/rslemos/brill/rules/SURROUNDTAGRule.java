package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class SURROUNDTAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag_1 = context.getToken(-1).getTag();
			String tag1 = context.getToken(1).getTag();
			
			return new SURROUNDTAGRule(from, to, tag_1, tag1);
		}
		
	};

	private final String prev1Tag;
	private final String next1Tag;

	public SURROUNDTAGRule(String from, String to, String prev1Tag, String next1Tag) {
		super(from, to);
		this.prev1Tag = prev1Tag;
		this.next1Tag = next1Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag_1 = context.getToken(-1).getTag();
		String tag1 = context.getToken(1).getTag();
		
		return (prev1Tag != null ? prev1Tag.equals(tag_1) : tag_1 == null) &&
			(next1Tag != null ? next1Tag.equals(tag1) : tag1 == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		SURROUNDTAGRule other = (SURROUNDTAGRule) o;
		
		return (prev1Tag != null ? prev1Tag.equals(other.prev1Tag) : other.prev1Tag == null) &&
			(next1Tag != null ? next1Tag.equals(other.next1Tag) : other.next1Tag == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 13;
		hashCode += prev1Tag != null ? prev1Tag.hashCode() : 0;
		hashCode *= 7;
		hashCode += next1Tag != null ? next1Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev1Tag + " " + next1Tag;
	}
}
