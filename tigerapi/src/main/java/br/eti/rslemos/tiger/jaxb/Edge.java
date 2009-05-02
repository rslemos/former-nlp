package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlAttribute;

public class Edge {

	@XmlAttribute(required = true)
	public String idref;

	@XmlAttribute(required = false)
	public String label;
}
