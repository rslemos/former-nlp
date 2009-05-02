package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Match {

	private String subgraph;
	private List<Variable> variables;


	@XmlAttribute(required = true)
	public String getSubgraph() {
		return subgraph;
	}

	public void setSubgraph(String subgraph) {
		this.subgraph = subgraph;
	}

	@XmlElement(required = true, name = "variable")
	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}


}
