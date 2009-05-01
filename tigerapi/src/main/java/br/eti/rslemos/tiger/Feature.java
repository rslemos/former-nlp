package br.eti.rslemos.tiger;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Feature {

	private final String name;
	private final String domain;

	private final Map<String, FeatureValue> values = new LinkedHashMap<String, FeatureValue>();

	public Feature(String name, String domain) {
		this.name = name;
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public String getDomain() {
		return domain;
	}

	public Collection<FeatureValue> getValues() {
		return values.values();
	}

	public void add(FeatureValue featureValue) {
		values.put(featureValue.getName(), featureValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof Feature))
			return false;

		Feature other = (Feature)obj;

		return
		(name != null ? name.equals(other.name) : other.name == null) &&
		(domain != null ? domain.equals(other.domain) : other.domain == null) &&
		(values.equals(other.values));
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode += name != null ? name.hashCode() : 0;
		hashCode *= 3;
		hashCode += domain != null ? domain.hashCode() : 0;
		hashCode *= 5;
		hashCode += values.hashCode();

		return hashCode;
	}

	@Override
	public String toString() {
		return name + "(" + domain + ")";
	}

}
