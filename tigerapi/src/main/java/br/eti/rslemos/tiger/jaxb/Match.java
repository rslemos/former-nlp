package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Match {

	@XmlAttribute(required = true)
	public String subgraph;

	@XmlElement(required = true, name = "variable")
	public List<Variable> variables;
}
