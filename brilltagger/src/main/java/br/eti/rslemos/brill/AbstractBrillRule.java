package br.eti.rslemos.brill;

public abstract class AbstractBrillRule extends AbstractRule {
	public AbstractBrillRule() {
		super();
	}

	public AbstractBrillRule(Object from, Object to) {
		super(from, to);
	}

	@Override
	public String toString() {
		return toBrillString();
	}
	
	public String toBrillString() {
		return from + " " + to + " " + getType();
	}

	protected final String getType() {
		String simpleName = getClass().getSimpleName();
		if (simpleName.endsWith("Rule"))
			return simpleName.substring(0, simpleName.length() - 4);
		else
			return simpleName;
	}
	
	
}
