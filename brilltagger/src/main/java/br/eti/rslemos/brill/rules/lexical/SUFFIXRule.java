package br.eti.rslemos.brill.rules.lexical;

import java.util.ArrayList;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.AbstractRuleFactory;
import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.brill.rules.SerializableAsBrillText;

public class SUFFIXRule<T> extends AbstractRule<T> implements SerializableAsBrillText {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new AbstractRuleFactory<T1>() {
			@Override
			public Collection<Rule<T1>> create(T1 from, T1 to, Context<T1> context) {
				return create(from, to, context.getToken(0).getWord());
			}

			public Collection<Rule<T1>> create(T1 from, T1 to, String word0) {
				Collection<Rule<T1>> result = new ArrayList<Rule<T1>>(word0.length() - 1);

				for(int i = 1; i < word0.length(); i++) {
					result.add(new SUFFIXRule<T1>(from, to, word0.substring(word0.length() - i, word0.length())));
				}
				
				return result;
			}
		};
	}

	private final String suffix;

	public SUFFIXRule(T fromTag, T toTag, String suffix) {
		super(fromTag, toTag);
		
		this.suffix = suffix;
	}

	@Override
	protected boolean thisMatches(String word0) {
		return suffix != null ? word0.endsWith(suffix) : word0 == null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		SUFFIXRule other = (SUFFIXRule) o;
		
		return suffix != null ? suffix.equals(other.suffix) : other.suffix == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 103;
		hashCode += suffix != null ? suffix.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + suffix;
	}

}
