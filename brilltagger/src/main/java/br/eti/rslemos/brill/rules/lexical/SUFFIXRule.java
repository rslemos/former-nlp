package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.AbstractRuleFactory;
import br.eti.rslemos.brill.rules.RuleCreationException;
import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.brill.rules.SerializableAsBrillText;

public class SUFFIXRule<T> extends AbstractRule<T> implements SerializableAsBrillText {

	private static final class SUFFIXRuleFactory<T1> extends AbstractRuleFactory<T1> {
		private final int length;

		private SUFFIXRuleFactory(int length) {
			this.length = length;
		}

		@Override
		public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
			return create(from, to, context.getToken(0).getWord());
		}

		public Rule<T1> create(T1 from, T1 to, String word0) throws RuleCreationException {
			if (word0.length() > length)
				return new SUFFIXRule<T1>(from, to, word0.substring(word0.length() - length, word0.length()));
			else
				throw new RuleCreationException();
		}
	}

	public static final RuleFactory<String> FACTORY1 = new SUFFIXRuleFactory<String>(1);
	public static final RuleFactory<String> FACTORY2 = new SUFFIXRuleFactory<String>(2);
	public static final RuleFactory<String> FACTORY3 = new SUFFIXRuleFactory<String>(3);
	public static final RuleFactory<String> FACTORY4 = new SUFFIXRuleFactory<String>(4);
	public static final RuleFactory<String> FACTORY5 = new SUFFIXRuleFactory<String>(5);
	public static final RuleFactory<String> FACTORY6 = new SUFFIXRuleFactory<String>(6);
	public static final RuleFactory<String> FACTORY7 = new SUFFIXRuleFactory<String>(7);
	public static final RuleFactory<String> FACTORY8 = new SUFFIXRuleFactory<String>(8);
	public static final RuleFactory<String> FACTORY9 = new SUFFIXRuleFactory<String>(9);
	
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
