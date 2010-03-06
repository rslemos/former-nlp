package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class CURWDRule<T> extends AbstractRule<T> implements SerializableAsBrillText {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
	
			public Rule<T1> create(T1 from, T1 to, Context<T1> context) {
				String word0 = context.getToken(0).getWord();
				
				return new CURWDRule<T1>(from, to, word0);
			}
			
		};
	}
	
	private final String word;

	public CURWDRule(T from, T to, String word) {
		super(from, to);
		this.word = word;
	}

	public boolean matches(Context<T> context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context<T> context) {
		String word0 = context.getToken(0).getWord();
		
		return word != null ? word.equals(word0) : word0 == null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		CURWDRule other = (CURWDRule) o;
		
		return word != null ? word.equals(other.word) : other.word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 5;
		hashCode += word != null ? word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + word;
	}
}
