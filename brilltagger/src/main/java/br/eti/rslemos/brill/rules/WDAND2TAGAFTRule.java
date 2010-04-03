package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class WDAND2TAGAFTRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new WDAND2TAGAFTRuleFactory();
	}
	
	private final String word;
	private final Object next2Object;

	public WDAND2TAGAFTRule(Object from, Object to, String word, Object next2Object) {
		super(from, to);
		this.word = word;
		this.next2Object = next2Object;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		Object tag2 = context.getToken(2).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next2Object != null ? next2Object.equals(tag2) : tag2 == null);
	}
	
	@Override
	public boolean firingDependsOnObject(Object tag) {
		return super.firingDependsOnObject(tag) || 
			(next2Object != null ? next2Object.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		WDAND2TAGAFTRule other = (WDAND2TAGAFTRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(next2Object != null ? next2Object.equals(other.next2Object) : other.next2Object == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 19;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 7;
		hashCode += next2Object != null ? next2Object.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + word + " " + next2Object;
	}
}
