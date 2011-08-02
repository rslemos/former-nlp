package br.eti.rslemos.brill;

import java.io.IOException;
import java.io.Writer;

import br.eti.rslemos.tagger.AbstractToken;

public abstract class AbstractRule implements Rule {

	protected Object from;
	protected Object to;

	public AbstractRule () {
	}

	public AbstractRule (Object from, Object to) {
		this.from = from;
		this.to = to;
	}

	public final Object getFrom() {
		return from;
	}

	public final Object getTo() {
		return to;
	}

	public boolean matches(Context context) {
		Object tag0 = context.getToken(0).getFeature(AbstractToken.POS);
		
		return from != null ? from.equals(tag0) : tag0 == null;
	}

	public boolean testsTag(Object tag) {
		return from != null ? from.equals(tag) : tag == null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
	
		if (o == null)
			return false;
	
		if (this.getClass() != o.getClass())
			return false;
	
		AbstractRule other = (AbstractRule)o;
	
		return (to != null ? to.equals(other.to) : other.to == null) &&
			(from != null ? from.equals(other.from) : other.from == null);
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
	
		hashCode += this.getClass().hashCode();
		hashCode *= 3;
		hashCode += to != null ? to.hashCode() : 0;
		hashCode *= 3;
		hashCode += from != null ? from.hashCode() : 0;
	
		return hashCode;
	}

	protected String toBrillString() {
		return from + " " + to + " " + getType();
	}

	protected final String getType() {
		String simpleName = getClass().getSimpleName();
		if (simpleName.endsWith("Rule"))
			return simpleName.substring(0, simpleName.length() - 4);
		else
			return simpleName;
	}

	public void writeRule(Writer out) throws IOException {
		out.write(toString());
		out.write('\n');
	}

	public String toString() {
		return toBrillString();
	}

}
