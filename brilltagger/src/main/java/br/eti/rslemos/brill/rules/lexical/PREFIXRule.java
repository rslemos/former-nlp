package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.AbstractRuleFactory;
import br.eti.rslemos.brill.rules.RuleCreationException;
import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.brill.rules.SerializableAsBrillText;

public class PREFIXRule<T> extends AbstractRule<T> implements SerializableAsBrillText {

	private static final class PREFIXRuleFactory<T1> extends AbstractRuleFactory<T1> {
		private final int length;

		private PREFIXRuleFactory(int length) {
			this.length = length;
		}

		@Override
		public Rule<T1> create(T1 from, T1 to, Context<T1> context) throws RuleCreationException {
			return create(from, to, context.getToken(0).getWord());
		}

		public Rule<T1> create(T1 from, T1 to, String word0) throws RuleCreationException {
			if (word0.length() > length)
				return new PREFIXRule<T1>(from, to, word0.substring(0, length));
			else
				throw new RuleCreationException();
		}
	}

	public static final RuleFactory<String> FACTORY1 = new PREFIXRuleFactory<String>(1);
	public static final RuleFactory<String> FACTORY2 = new PREFIXRuleFactory<String>(2);
	public static final RuleFactory<String> FACTORY3 = new PREFIXRuleFactory<String>(3);
	public static final RuleFactory<String> FACTORY4 = new PREFIXRuleFactory<String>(4);
	public static final RuleFactory<String> FACTORY5 = new PREFIXRuleFactory<String>(5);
	public static final RuleFactory<String> FACTORY6 = new PREFIXRuleFactory<String>(6);
	public static final RuleFactory<String> FACTORY7 = new PREFIXRuleFactory<String>(7);
	public static final RuleFactory<String> FACTORY8 = new PREFIXRuleFactory<String>(8);
	public static final RuleFactory<String> FACTORY9 = new PREFIXRuleFactory<String>(9);
	
	private final String prefix;

	public PREFIXRule(T fromTag, T toTag, String prefix) {
		super(fromTag, toTag);
		
		this.prefix = prefix;
	}

	@Override
	protected boolean thisMatches(String word0) {
		return prefix != null ? word0.startsWith(prefix) : word0 == null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREFIXRule other = (PREFIXRule) o;
		
		return prefix != null ? prefix.equals(other.prefix) : other.prefix == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 101;
		hashCode += prefix != null ? prefix.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prefix;
	}

}
