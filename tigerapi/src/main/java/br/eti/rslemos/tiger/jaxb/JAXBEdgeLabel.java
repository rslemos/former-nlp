package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.EdgeLabel;

@XmlType(name = "edgelabelType")
public class JAXBEdgeLabel implements EdgeLabel {

	private List<JAXBFeatureValue> values;


	@XmlElement(required = true, name = "value")
	public List<JAXBFeatureValue> getValues() {
		return values;
	}

	public void setValues(List<JAXBFeatureValue> values) {
		this.values = values;
	}


}
