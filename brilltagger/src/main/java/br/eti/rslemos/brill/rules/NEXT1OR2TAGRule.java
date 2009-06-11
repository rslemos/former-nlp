package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2TAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final RuleFactory FACTORY1 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag1 = context.getToken(1).getTag();
			return new NEXT1OR2TAGRule(from, to, tag1);
		}
		
	};

	public static final RuleFactory FACTORY2 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag2 = context.getToken(2).getTag();
			return new NEXT1OR2TAGRule(from, to, tag2);
		}
		
	};

	private final String next1or2Tag;

	public NEXT1OR2TAGRule(String from, String to, String next1or2Tag) {
		super(from, to);
		
		this.next1or2Tag = next1or2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag1 = context.getToken(1).getTag();
		String tag2 = context.getToken(2).getTag();
		
		return next1or2Tag != null 
		? (next1or2Tag.equals(tag1) | next1or2Tag.equals(tag2)) 
		: (tag1 == null | tag2 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(String tag) {
		return super.firingDependsOnTag(tag) || 
			(next1or2Tag != null ? next1or2Tag.equals(tag) : tag == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT1OR2TAGRule other = (NEXT1OR2TAGRule) o;
		
		return next1or2Tag != null ? next1or2Tag.equals(other.next1or2Tag) : other.next1or2Tag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 11;
		hashCode += next1or2Tag != null ? next1or2Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + next1or2Tag;
	}
}
