package br.eti.rslemos.tiger.jaxb;

import java.net.URI;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "headType", propOrder = {"meta", "annotation"})
public class Head {

	private URI external;
	private Meta meta;
	private Annotation annotation;


	@XmlAttribute(required = false)
	public URI getExternal() {
		return external;
	}

	public void setExternal(URI external) {
		this.external = external;
	}

	@XmlElement(required = false)
	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	@XmlElement(required = false)
	public Annotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}
}
