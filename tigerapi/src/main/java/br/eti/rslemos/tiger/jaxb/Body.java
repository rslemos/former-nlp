package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "bodyType")
public class Body {

	private List<Sentence> sentences;


	@XmlElement(required = true, name = "s")
	public List<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}
}
