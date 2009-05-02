package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "sentenceType", propOrder = {"graph", "matches"})
public class Sentence {

	private String id;
	private Graph graph;
	private List<Match> matches;


	@XmlAttribute(required = true)
	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(required = false)
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	@XmlElementWrapper(required = false)
	@XmlElement(required = true, name = "match")
	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}


}
