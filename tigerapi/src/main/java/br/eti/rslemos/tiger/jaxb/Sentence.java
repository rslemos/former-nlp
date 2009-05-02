package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "sentenceType", propOrder = {"graph", "matches"})
public class Sentence {

	@XmlAttribute(required = true)
	@XmlID
	public String id;

	@XmlElement(required = false)
	public Graph graph;

	@XmlElementWrapper(required = false)
	@XmlElement(required = true, name = "match")
	public List<Match> matches;
}
