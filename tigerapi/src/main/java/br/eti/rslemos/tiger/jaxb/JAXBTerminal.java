package br.eti.rslemos.tiger.jaxb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import br.eti.rslemos.tiger.Terminal;

@XmlType(name = "tType")
public class JAXBTerminal implements Terminal {

	private String id;
	private Map<QName, String> features = new HashMap<QName, String>();
	private List<JAXBSecEdge> secEdges;


	@XmlAttribute(required = true)
	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAnyAttribute
	public Map<QName, String> getFeatures() {
		return features;
	}

	public void setFeatures(Map<QName, String> features) {
		this.features = features;
	}

	@XmlElement(required = false, name = "secedge")
	public List<JAXBSecEdge> getSecEdges() {
		return secEdges;
	}

	public void setSecEdges(List<JAXBSecEdge> secEdges) {
		this.secEdges = secEdges;
	}


}
