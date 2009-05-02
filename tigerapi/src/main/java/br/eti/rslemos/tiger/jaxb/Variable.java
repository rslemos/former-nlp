package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlAttribute;

public class Variable {
	@XmlAttribute(required = true)
	public String name;

	@XmlAttribute(required = true)
	public String idref;
}
