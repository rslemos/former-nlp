package br.eti.rslemos.tiger.jaxb;

import java.net.URI;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "headType", propOrder = {"meta", "annotation"})
public class Head {

	@XmlAttribute(required = false)
	public URI external;

	@XmlElement(required = false)
	public Meta meta;

	@XmlElement(required = false)
	public Annotation annotation;
}
