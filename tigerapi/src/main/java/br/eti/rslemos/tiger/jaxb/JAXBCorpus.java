package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Corpus;

@XmlRootElement(name = "corpus")
@XmlType(name = "", propOrder = {"head", "body"})
public class JAXBCorpus implements Corpus {

	private String id;
	private String version;
	private JAXBHead head;
	private JAXBBody body;

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
	public JAXBHead getHead() {
		return head;
	}

	public void setHead(JAXBHead head) {
		this.head = head;
	}

	@XmlElement(required = true)
	public JAXBBody getBody() {
		return body;
	}

	public void setBody(JAXBBody body) {
		this.body = body;
	}
}
