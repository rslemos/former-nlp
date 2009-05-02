package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(name = "featurevalueType")
public class FeatureValue {
	@XmlAttribute
	public String name;

	@XmlValue
	public String value;
}
