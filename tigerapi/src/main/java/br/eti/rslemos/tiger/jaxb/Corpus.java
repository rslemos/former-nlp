package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "corpus")
@XmlType(name = "", propOrder = {"head", "body"})
public class Corpus {

	private String id;
	private String version;
	private Head head;
	private Body body;

	@XmlAttribute(required = true)
	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute(required = false)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@XmlElement(required = false)
	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	@XmlElement(required = true)
	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
}
