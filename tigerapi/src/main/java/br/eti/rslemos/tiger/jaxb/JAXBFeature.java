package br.eti.rslemos.tiger.jaxb;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Domain;
import br.eti.rslemos.tiger.Feature;

@XmlType(name = "featureType")
public class JAXBFeature implements Feature {

	private String name;
	private Domain domain;
	private List<JAXBFeatureValue> values;


	@XmlAttribute(required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(required = true)
	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	@XmlElement(required = false, name = "value", nillable = false)
	public List<JAXBFeatureValue> getValues() {
		if (values == null)
			values = new LinkedList<JAXBFeatureValue>();

		return values;
	}

	public void setValues(List<JAXBFeatureValue> values) {
		this.values = values;
	}


}
