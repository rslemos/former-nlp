package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import br.eti.rslemos.tiger.Match;

public class JAXBMatch implements Match {

	private String subgraph;
	private List<JAXBVariable> variables;


	@XmlAttribute(required = true)
	public String getSubgraph() {
		return subgraph;
	}

	public void setSubgraph(String subgraph) {
		this.subgraph = subgraph;
	}

	@XmlElement(required = true, name = "variable")
	public List<JAXBVariable> getVariables() {
		return variables;
	}

	public void setVariables(List<JAXBVariable> variables) {
		this.variables = variables;
	}


}
