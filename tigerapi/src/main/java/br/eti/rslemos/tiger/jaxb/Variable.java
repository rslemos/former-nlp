package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlAttribute;

public class Variable {

	private String name;
	private String idref;


	@XmlAttribute(required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(required = true)
	public String getIdref() {
		return idref;
	}

	public void setIdref(String idref) {
		this.idref = idref;
	}


}
