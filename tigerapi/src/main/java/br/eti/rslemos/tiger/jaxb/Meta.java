package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "metaType", propOrder = {"name", "author", "date", "description", "format", "history"})
public class Meta {

	@XmlElement(required = false)
	public String name;

	@XmlElement(required = false)
	public String author;

	@XmlElement(required = false)
	public String date;

	@XmlElement(required = false)
	public String description;

	@XmlElement(required = false)
	public String format;

	@XmlElement(required = false)
	public String history;
}
