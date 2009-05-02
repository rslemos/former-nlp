package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Annotation;

@XmlType(name = "annotationType", propOrder = {"features", "edgeLabel", "secEdgeLabel"})
public class JAXBAnnotation implements Annotation {

	private List<JAXBFeature> features;
	private JAXBEdgeLabel edgeLabel;
	private JAXBEdgeLabel secEdgeLabel;


	@XmlElement(required = true, name = "feature")
	public List<JAXBFeature> getFeatures() {
		return features;
	}

	public void setFeatures(List<JAXBFeature> features) {
		this.features = features;
	}

	@XmlElement(required = false, name = "edgelabel")
	public JAXBEdgeLabel getEdgeLabel() {
		return edgeLabel;
	}

	public void setEdgeLabel(JAXBEdgeLabel edgeLabel) {
		this.edgeLabel = edgeLabel;
	}

	@XmlElement(required = false, name = "secedgelabel")
	public JAXBEdgeLabel getSecEdgeLabel() {
		return secEdgeLabel;
	}

	public void setSecEdgeLabel(JAXBEdgeLabel secEdgeLabel) {
		this.secEdgeLabel = secEdgeLabel;
	}


}
