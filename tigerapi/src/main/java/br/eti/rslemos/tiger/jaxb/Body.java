package br.eti.rslemos.tiger.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "bodyType")
public class Body {
	@XmlElement(required = true, name = "s")
	public List<Sentence> sentences;
}
