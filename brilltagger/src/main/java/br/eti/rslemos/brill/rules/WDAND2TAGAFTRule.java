package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.tagger.Tag;

public class WDAND2TAGAFTRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new WDAND2TAGAFTRuleFactory();
	}
	
	private final String word;
	private final Tag next2Tag;

	public WDAND2TAGAFTRule(Tag from, Tag to, String word, Tag next2Tag) {
		super(from, to);
		this.word = word;
		this.next2Tag = next2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		Tag tag2 = context.getToken(2).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next2Tag != null ? next2Tag.equals(tag2) : tag2 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(Tag tag) {
		return super.firingDependsOnTag(tag) || 
			(next2Tag != null ? next2Tag.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		WDAND2TAGAFTRule other = (WDAND2TAGAFTRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(next2Tag != null ? next2Tag.equals(other.next2Tag) : other.next2Tag == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 19;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 7;
		hashCode += next2Tag != null ? next2Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + word + " " + next2Tag;
	}
}
