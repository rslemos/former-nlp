package br.eti.rslemos.brill.rules.lexical;


import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.brill.rules.SerializableAsBrillText;

public class PREFIXRule<T> extends AbstractRule<T> implements SerializableAsBrillText {
	public static final <T1> RuleFactory<T1> FACTORY() {
		return new PREFIXRuleFactory<T1>();
	}

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
