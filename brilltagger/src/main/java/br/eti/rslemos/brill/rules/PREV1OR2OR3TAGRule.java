package br.eti.rslemos.brill.rules;


import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.tagger.Tag;

public class PREV1OR2OR3TAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new PREV1OR2OR3TAGRuleFactory();
	}
	
	private final Tag prev1or2or3Tag;

	public PREV1OR2OR3TAGRule(Tag from, Tag to, Tag prev1or2or3Tag) {
		super(from, to);
		
		this.prev1or2or3Tag = prev1or2or3Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Tag tag_1 = context.getToken(-1).getTag();
		Tag tag_2 = context.getToken(-2).getTag();
		Tag tag_3 = context.getToken(-3).getTag();
		
		return prev1or2or3Tag != null 
		? (prev1or2or3Tag.equals(tag_1) | prev1or2or3Tag.equals(tag_2) | prev1or2or3Tag.equals(tag_3)) 
		: (tag_1 == null | tag_2 == null | tag_3 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(Tag tag) {
		return super.firingDependsOnTag(tag) || 
			(prev1or2or3Tag != null ? prev1or2or3Tag.equals(tag) : tag == null);
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

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev1or2or3Tag;
	}
}
