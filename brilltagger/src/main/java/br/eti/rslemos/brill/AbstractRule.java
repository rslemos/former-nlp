package br.eti.rslemos.brill;

public abstract class AbstractRule implements Rule {
	private Token target;

	public AbstractRule () {
	}

	public AbstractRule (Token target) {
		this.target = target;
	}

	public Token getTarget() {
		return target;
	}

	public void setTarget(Token target) {
		this.target = target;
	}

	public abstract boolean matches();

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null)
			return false;

		if (this.getClass() != o.getClass())
			return false;

		AbstractRule other = (AbstractRule)o;

		return target != null ? target.equals(other.target) : other.target == null;
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode += this.getClass().hashCode();
		hashCode *= 3;
		hashCode += target != null ? target.hashCode() : 0;

		return hashCode;
	}
}
