package br.eti.rslemos.brill.rules.lexical;



public class SUFFIXRule extends AbstractLexicalBrillRule {
	public static SUFFIXRule createRule(Object fromObject, Object toObject,
			String suffix) {
		return new SUFFIXRule(fromObject, toObject, suffix);
	}

	private final String suffix;

	private SUFFIXRule(Object fromObject, Object toObject, String suffix) {
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
	public String toBrillString() {
		return super.toBrillString() + " " + suffix;
	}

}
