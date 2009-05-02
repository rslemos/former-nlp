package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "graphType", propOrder = {"terminals", "nonTerminals"})
public class Graph {

	@XmlAttribute(required = true)
	public String root;

	@XmlAttribute
	public Boolean discontinuous;

	@XmlElementWrapper(name = "terminals", required = true)
	@XmlElement(name = "t", required = true)
	public List<Terminal> terminals;

	@XmlElementWrapper(name = "nonterminals", required = true)
	@XmlElement(name = "nt", required = false)
	public List<NonTerminal> nonTerminals;
}
