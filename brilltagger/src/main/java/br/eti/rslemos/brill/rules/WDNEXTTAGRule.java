package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDNEXTTAGRule<T> extends AbstractRule<T> implements SerializableAsBrillText  {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
				String word0 = context.getToken(0).getWord();
				T1 tag1 = context.getToken(1).getTag();
				
				return new WDNEXTTAGRule<T1>(from, to, word0, tag1);
			}
			
		};
	}
	
	private final String word;
	private final T next1Tag;

	public WDNEXTTAGRule(T from, T to, String word, T next1Tag) {
		super(from, to);
		this.word = word;
		this.next1Tag = next1Tag;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word0 = context.getToken(0).getWord();
		T tag1 = context.getToken(1).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next1Tag != null ? next1Tag.equals(tag1) : tag1 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(T tag) {
		return super.firingDependsOnTag(tag) || 
			(next1Tag != null ? next1Tag.equals(tag) : tag == null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		WDNEXTTAGRule other = (WDNEXTTAGRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(next1Tag != null ? next1Tag.equals(other.next1Tag) : other.next1Tag == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 19;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 13;
		hashCode += next1Tag != null ? next1Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + word + " " + next1Tag;
	}
}
