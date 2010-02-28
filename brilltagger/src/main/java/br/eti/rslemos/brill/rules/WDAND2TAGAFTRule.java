package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGAFTRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word0 = context.getToken(0).getWord();
				T1 tag2 = context.getToken(2).getTag();
				
				return new WDAND2TAGAFTRule<T1>(from, to, word0, tag2);
			}
			
		};
	}
	
	private final String word;
	private final T next2Tag;

	public WDAND2TAGAFTRule(T from, T to, String word, T next2Tag) {
		super(from, to);
		this.word = word;
		this.next2Tag = next2Tag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word0 = context.getToken(0).getWord();
		T tag2 = context.getToken(2).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next2Tag != null ? next2Tag.equals(tag2) : tag2 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(T tag) {
		return super.firingDependsOnTag(tag) || 
			(next2Tag != null ? next2Tag.equals(tag) : tag == null);
	}

	@SuppressWarnings("unchecked")
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
