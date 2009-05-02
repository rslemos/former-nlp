package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Graph;

@XmlType(name = "graphType", propOrder = {"terminals", "nonTerminals"})
public class JAXBGraph implements Graph {

	private String root;
	private Boolean discontinuous;
	private List<JAXBTerminal> terminals;
	private List<JAXBNonTerminal> nonTerminals;


	@XmlAttribute(required = true)
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	@XmlAttribute
	public Boolean getDiscontinuous() {
		return discontinuous;
	}

	public void setDiscontinuous(Boolean discontinuous) {
		this.discontinuous = discontinuous;
	}

	@XmlElementWrapper(name = "terminals", required = true)
	@XmlElement(name = "t", required = true)
	public List<JAXBTerminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(List<JAXBTerminal> terminals) {
		this.terminals = terminals;
	}

	@XmlElementWrapper(name = "nonterminals", required = true)
	@XmlElement(name = "nt", required = false)
	public List<JAXBNonTerminal> getNonTerminals() {
		return nonTerminals;
	}

	public void setNonTerminals(List<JAXBNonTerminal> nonTerminals) {
		this.nonTerminals = nonTerminals;
	}


}
