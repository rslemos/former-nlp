package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlAttribute;

public class SecEdge {

	@XmlAttribute(required = true)
	public String idref;

	@XmlAttribute(required = false)
	public String label;
}
