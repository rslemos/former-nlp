package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Sentence;

@XmlType(name = "sentenceType", propOrder = {"graph", "matches"})
public class JAXBSentence implements Sentence {

	private String id;
	private JAXBGraph graph;
	private List<JAXBMatch> matches;


	@XmlAttribute(required = true)
	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(required = false)
	public JAXBGraph getGraph() {
		return graph;
	}

	public void setGraph(JAXBGraph graph) {
		this.graph = graph;
	}

	@XmlElementWrapper(required = false)
	@XmlElement(required = true, name = "match")
	public List<JAXBMatch> getMatches() {
		return matches;
	}

	public void setMatches(List<JAXBMatch> matches) {
		this.matches = matches;
	}


}
