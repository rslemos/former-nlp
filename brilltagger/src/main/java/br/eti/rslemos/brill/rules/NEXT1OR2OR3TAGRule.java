package br.eti.rslemos.brill.rules;


import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.tagger.Tag;

public class NEXT1OR2OR3TAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new NEXT1OR2OR3TAGRuleFactory();
	}
	
	private final Tag next1or2or3Tag;

	public NEXT1OR2OR3TAGRule(Tag from, Tag to, Tag next1or2or3Tag) {
		super(from, to);
		
		this.next1or2or3Tag = next1or2or3Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Tag tag1 = context.getToken(1).getTag();
		Tag tag2 = context.getToken(2).getTag();
		Tag tag3 = context.getToken(3).getTag();
		
		return next1or2or3Tag != null 
		? (next1or2or3Tag.equals(tag1) | next1or2or3Tag.equals(tag2) | next1or2or3Tag.equals(tag3)) 
		: (tag1 == null | tag2 == null | tag3 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(Tag tag) {
		return super.firingDependsOnTag(tag) || 
			(next1or2or3Tag != null ? next1or2or3Tag.equals(tag) : tag == null);
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

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + next1or2or3Tag;
	}
}
