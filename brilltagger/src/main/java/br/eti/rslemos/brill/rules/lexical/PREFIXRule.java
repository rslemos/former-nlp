package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.AbstractRuleFactory;
import br.eti.rslemos.brill.rules.RuleCreationException;
import br.eti.rslemos.brill.rules.SerializableAsBrillText;

public class PREFIXRule extends AbstractRule implements SerializableAsBrillText {

	private static final class PREFIXRuleFactory extends AbstractRuleFactory {
		private final int length;

		private PREFIXRuleFactory(int length) {
			this.length = length;
		}

		@Override
		public Rule create(String from, String to, Context context) throws RuleCreationException {
			return create(from, to, context.getToken(0).getWord());
		}

		public Rule create(String from, String to, String word0) throws RuleCreationException {
			if (word0.length() > length)
				return new PREFIXRule(from, to, word0.substring(0, length));
			else
				throw new RuleCreationException();
		}
	}

	public static final AbstractRuleFactory FACTORY1 = new PREFIXRuleFactory(1);
	public static final AbstractRuleFactory FACTORY2 = new PREFIXRuleFactory(2);
	public static final AbstractRuleFactory FACTORY3 = new PREFIXRuleFactory(3);
	public static final AbstractRuleFactory FACTORY4 = new PREFIXRuleFactory(4);
	public static final AbstractRuleFactory FACTORY5 = new PREFIXRuleFactory(5);
	public static final AbstractRuleFactory FACTORY6 = new PREFIXRuleFactory(6);
	public static final AbstractRuleFactory FACTORY7 = new PREFIXRuleFactory(7);
	public static final AbstractRuleFactory FACTORY8 = new PREFIXRuleFactory(8);
	public static final AbstractRuleFactory FACTORY9 = new PREFIXRuleFactory(9);
	
	private final String prefix;

	public PREFIXRule(String fromTag, String toTag, String prefix) {
		super(fromTag, toTag);
		
		this.prefix = prefix;
	}

	@Override
	protected boolean thisMatches(String word0) {
		return prefix != null ? word0.startsWith(prefix) : word0 == null;
	}

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
