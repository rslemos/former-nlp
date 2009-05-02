package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "edgelabelType")
public class EdgeLabel {

	@XmlElement(required = true, name = "value")
	List<FeatureValue> values;
}
