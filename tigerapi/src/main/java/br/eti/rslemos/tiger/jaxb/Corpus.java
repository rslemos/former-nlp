package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "corpus")
@XmlType(name = "", propOrder = {"head", "body"})
public class Corpus {
	@XmlAttribute(required = true)
	@XmlID
	public String id;

	@XmlAttribute(required = false)
	public String version;

	@XmlElement(required = false)
	public Head head;

	@XmlElement(required = true)
	public Body body;
}
