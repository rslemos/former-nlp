package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.tagger.Tag;

public class PREV2TAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new PREV2TAGRuleFactory();
	}
	
	private final Tag prev2Tag;

	public PREV2TAGRule(Tag from, Tag to, Tag prev2Tag) {
		super(from, to);
		this.prev2Tag = prev2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Tag tag_2 = context.getToken(-2).getTag();
		
		return prev2Tag != null ? prev2Tag.equals(tag_2) : tag_2 == null;
	}
	
	@Override
	public boolean firingDependsOnTag(Tag tag) {
		return super.firingDependsOnTag(tag) || 
			(prev2Tag != null ? prev2Tag.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV2TAGRule other = (PREV2TAGRule) o;
		
		return prev2Tag != null ? prev2Tag.equals(other.prev2Tag) : other.prev2Tag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 43;
		hashCode += prev2Tag != null ? prev2Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev2Tag;
	}
}
