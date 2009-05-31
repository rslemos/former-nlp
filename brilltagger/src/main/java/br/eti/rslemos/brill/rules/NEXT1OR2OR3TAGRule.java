package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3TAGRule extends AbstractRule {
	public static final RuleFactory FACTORY1 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag1 = context.getToken(1).getTag();
			return new NEXT1OR2OR3TAGRule(from, to, tag1);
		}
		
	};

	public static final RuleFactory FACTORY2 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag2 = context.getToken(2).getTag();
			return new NEXT1OR2OR3TAGRule(from, to, tag2);
		}
		
	};

	public static final RuleFactory FACTORY3 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag3 = context.getToken(3).getTag();
			return new NEXT1OR2OR3TAGRule(from, to, tag3);
		}
		
	};

	private final String next1or2or3Tag;

	public NEXT1OR2OR3TAGRule(String from, String to, String next1or2or3Tag) {
		super(from, to);
		
		this.next1or2or3Tag = next1or2or3Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag1 = context.getToken(1).getTag();
		String tag2 = context.getToken(2).getTag();
		String tag3 = context.getToken(3).getTag();
		
		return next1or2or3Tag != null 
		? (next1or2or3Tag.equals(tag1) | next1or2or3Tag.equals(tag2) | next1or2or3Tag.equals(tag3)) 
		: (tag1 == null | tag2 == null | tag3 == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXT1OR2OR3TAGRule other = (NEXT1OR2OR3TAGRule) o;
		
		return next1or2or3Tag != null ? next1or2or3Tag.equals(other.next1or2or3Tag) : other.next1or2or3Tag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 7;
		hashCode += next1or2or3Tag != null ? next1or2or3Tag.hashCode() : 0;
		
		return hashCode;
	}
}
