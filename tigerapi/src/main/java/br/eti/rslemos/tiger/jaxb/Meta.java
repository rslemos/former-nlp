package br.eti.rslemos.tiger.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "metaType", propOrder = {"name", "author", "date", "description", "format", "history"})
public class Meta {

	private String name;
	private String author;
	private String date;
	private String description;
	private String format;
	private String history;


	@XmlElement(required = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(required = false)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@XmlElement(required = false)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@XmlElement(required = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(required = false)
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@XmlElement(required = false)
	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}


}
