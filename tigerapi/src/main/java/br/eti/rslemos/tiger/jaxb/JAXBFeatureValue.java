package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import br.eti.rslemos.tiger.FeatureValue;

@XmlType(name = "featurevalueType")
public class JAXBFeatureValue implements FeatureValue {

	private String name;
	private String value;


	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlValue
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return name;
	}
}
