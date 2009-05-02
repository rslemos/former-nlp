package br.eti.rslemos.tiger.stax;

import br.eti.rslemos.tiger.FeatureValue;

public class StAXFeatureValue implements FeatureValue {

	private final String name;
	private final String comment;

	public StAXFeatureValue(String name, String comment) {
		this.name = name;
		this.comment = comment;
	}

	public StAXFeatureValue(String name) {
		this(name, "");
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof StAXFeatureValue))
			return false;

		StAXFeatureValue other = (StAXFeatureValue)obj;

		return
		(name != null ? name.equals(other.name) : other.name == null) &&
		(comment != null ? comment.equals(other.comment) : other.comment == null);
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode += name != null ? name.hashCode() : 0;
		hashCode *= 3;
		hashCode += comment != null ? comment.hashCode() : 0;

		return hashCode;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getValue() {
		return getComment();
	}


}
