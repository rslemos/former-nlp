package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.eti.rslemos.tiger.Body;

@XmlType(name = "bodyType")
public class JAXBBody implements Body {

	private List<JAXBSentence> sentences;


	@XmlElement(required = true, name = "s")
	public List<JAXBSentence> getSentences() {
		return sentences;
	}

	public void setSentences(List<JAXBSentence> sentences) {
		this.sentences = sentences;
	}
}
