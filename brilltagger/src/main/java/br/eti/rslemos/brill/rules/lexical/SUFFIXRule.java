package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.AbstractRuleFactory;
import br.eti.rslemos.brill.rules.RuleCreationException;
import br.eti.rslemos.brill.rules.SerializableAsBrillText;

public class SUFFIXRule extends AbstractRule implements SerializableAsBrillText {

	private static final class SUFFIXRuleFactory extends AbstractRuleFactory {
		private final int length;

		private SUFFIXRuleFactory(int length) {
			this.length = length;
		}

		@Override
		public Rule create(String from, String to, Context context) throws RuleCreationException {
			return create(from, to, context.getToken(0).getWord());
		}

		public Rule create(String from, String to, String word0) throws RuleCreationException {
			if (word0.length() > length)
				return new SUFFIXRule(from, to, word0.substring(word0.length() - length, word0.length()));
			else
				throw new RuleCreationException();
		}
	}

	public static final AbstractRuleFactory FACTORY1 = new SUFFIXRuleFactory(1);
	public static final AbstractRuleFactory FACTORY2 = new SUFFIXRuleFactory(2);
	public static final AbstractRuleFactory FACTORY3 = new SUFFIXRuleFactory(3);
	public static final AbstractRuleFactory FACTORY4 = new SUFFIXRuleFactory(4);
	public static final AbstractRuleFactory FACTORY5 = new SUFFIXRuleFactory(5);
	public static final AbstractRuleFactory FACTORY6 = new SUFFIXRuleFactory(6);
	public static final AbstractRuleFactory FACTORY7 = new SUFFIXRuleFactory(7);
	public static final AbstractRuleFactory FACTORY8 = new SUFFIXRuleFactory(8);
	public static final AbstractRuleFactory FACTORY9 = new SUFFIXRuleFactory(9);
	
	private final String suffix;

	public SUFFIXRule(String fromTag, String toTag, String suffix) {
		super(fromTag, toTag);
		
		this.suffix = suffix;
	}

	@Override
	protected boolean thisMatches(String word0) {
		return suffix != null ? word0.endsWith(suffix) : word0 == null;
	}

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
