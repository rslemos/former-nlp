package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "annotationType", propOrder = {"features", "edgeLabel", "secEdgeLabel"})
public class Annotation {

	@XmlElement(required = true, name = "feature")
	public List<Feature> features;

	@XmlElement(required = false, name = "edgelabel")
	public EdgeLabel edgeLabel;

	@XmlElement(required = false, name = "secedgelabel")
	public EdgeLabel secEdgeLabel;
}
