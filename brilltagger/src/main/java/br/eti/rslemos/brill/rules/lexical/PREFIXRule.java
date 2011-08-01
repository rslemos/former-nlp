package br.eti.rslemos.brill.rules.lexical;



public class PREFIXRule extends AbstractLexicalBrillRule {
	public static PREFIXRule createRule(Object fromObject, Object toObject,
			String prefix) {
		return new PREFIXRule(fromObject, toObject, prefix);
	}

	private final String prefix;

	private PREFIXRule(Object fromObject, Object toObject, String prefix) {
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
	public String toBrillString() {
		return super.toBrillString() + " " + prefix;
	}

}
