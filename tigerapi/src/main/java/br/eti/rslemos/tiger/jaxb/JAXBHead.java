package br.eti.rslemos.tiger.jaxb;

import java.net.URI;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Head;

@XmlType(name = "headType", propOrder = {"meta", "annotation"})
public class JAXBHead implements Head {

	private URI external;
	private JAXBMeta meta;
	private JAXBAnnotation annotation;


	@XmlAttribute(required = false)
	public URI getExternal() {
		return external;
	}

	public void setExternal(URI external) {
		this.external = external;
	}

	@XmlElement(required = false)
	public JAXBMeta getMeta() {
		return meta;
	}

	public void setMeta(JAXBMeta meta) {
		this.meta = meta;
	}

	@XmlElement(required = false)
	public JAXBAnnotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(JAXBAnnotation annotation) {
		this.annotation = annotation;
	}
}
