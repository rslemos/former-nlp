package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlAttribute;

public class SecEdge {

	private String idref;
	private String label;

	@XmlAttribute(required = true)
	public String getIdref() {
		return idref;
	}

	public void setIdref(String idref) {
		this.idref = idref;
	}

	@XmlAttribute(required = false)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


}
