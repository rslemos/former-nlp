package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT2TAGRule extends AbstractRule implements SerializableAsBrillText  {
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
	
	@Override
	public boolean firingDependsOnTag(String tag) {
		return super.firingDependsOnTag(tag) || 
			(next2Tag != null ? next2Tag.equals(tag) : tag == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT2TAGRule other = (NEXT2TAGRule) o;
		
		return next2Tag != null ? next2Tag.equals(other.next2Tag) : other.next2Tag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 17;
		hashCode += next2Tag != null ? next2Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + next2Tag;
	}
}
