package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3TAGRule extends AbstractRule {
	public static final RuleFactory FACTORY1 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag_1 = context.getToken(-1).getTag();
			return new PREV1OR2OR3TAGRule(from, to, tag_1);
		}
		
	};

	public static final RuleFactory FACTORY2 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag_2 = context.getToken(-2).getTag();
			return new PREV1OR2OR3TAGRule(from, to, tag_2);
		}
		
	};

	public static final RuleFactory FACTORY3 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag_3 = context.getToken(-3).getTag();
			return new PREV1OR2OR3TAGRule(from, to, tag_3);
		}
		
	};

	private final String prev1or2or3Tag;

	public PREV1OR2OR3TAGRule(String from, String to, String prev1or2or3Tag) {
		super(from, to);
		
		this.prev1or2or3Tag = prev1or2or3Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag_1 = context.getToken(-1).getTag();
		String tag_2 = context.getToken(-2).getTag();
		String tag_3 = context.getToken(-3).getTag();
		
		return prev1or2or3Tag != null 
		? (prev1or2or3Tag.equals(tag_1) | prev1or2or3Tag.equals(tag_2) | prev1or2or3Tag.equals(tag_3)) 
		: (tag_1 == null | tag_2 == null | tag_3 == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV1OR2OR3TAGRule other = (PREV1OR2OR3TAGRule) o;
		
		return prev1or2or3Tag != null ? prev1or2or3Tag.equals(other.prev1or2or3Tag) : other.prev1or2or3Tag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 31;
		hashCode += prev1or2or3Tag != null ? prev1or2or3Tag.hashCode() : 0;
		
		return hashCode;
	}
}
