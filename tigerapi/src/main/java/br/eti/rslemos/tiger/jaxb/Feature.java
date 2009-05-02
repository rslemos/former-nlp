package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "featureType")
public class Feature {
	@XmlAttribute(required = true)
	public String name;

	@XmlAttribute(required = true)
	public Domain domain;

	@XmlElement(required = false, name = "value")
	public List<FeatureValue> values;
}
