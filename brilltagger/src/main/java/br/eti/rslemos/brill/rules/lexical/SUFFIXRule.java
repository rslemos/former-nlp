package br.eti.rslemos.brill.rules.lexical;


import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.brill.rules.SerializableAsBrillText;

public class SUFFIXRule extends AbstractRule implements SerializableAsBrillText {
	public static final RuleFactory FACTORY = SUFFIXRuleFactory.INSTANCE;

	private final String suffix;

	public SUFFIXRule(Object fromObject, Object toObject, String suffix) {
		super(fromObject, toObject);
		
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
