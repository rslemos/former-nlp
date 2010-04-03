package br.eti.rslemos.brill.rules.lexical;


import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.brill.rules.SerializableAsBrillText;

public class PREFIXRule extends AbstractRule implements SerializableAsBrillText {
	public static final  RuleFactory FACTORY() {
		return new PREFIXRuleFactory();
	}

	private final String prefix;

	public PREFIXRule(Object fromObject, Object toObject, String prefix) {
		super(fromObject, toObject);
		
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
