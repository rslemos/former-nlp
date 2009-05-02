package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "annotationType", propOrder = {"features", "edgeLabel", "secEdgeLabel"})
public class Annotation {

	private List<Feature> features;
	private EdgeLabel edgeLabel;
	private EdgeLabel secEdgeLabel;


	@XmlElement(required = true, name = "feature")
	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	@XmlElement(required = false, name = "edgelabel")
	public EdgeLabel getEdgeLabel() {
		return edgeLabel;
	}

	public void setEdgeLabel(EdgeLabel edgeLabel) {
		this.edgeLabel = edgeLabel;
	}

	@XmlElement(required = false, name = "secedgelabel")
	public EdgeLabel getSecEdgeLabel() {
		return secEdgeLabel;
	}

	public void setSecEdgeLabel(EdgeLabel secEdgeLabel) {
		this.secEdgeLabel = secEdgeLabel;
	}


}
