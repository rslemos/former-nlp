package br.eti.rslemos.tiger.stax;

import java.util.Formatter;

import br.eti.rslemos.tiger.Meta;

public class StAXMetadata implements Meta {
	private String name;
	private String author;
	private String date;
	private String description;
	private String format;
	private String history;

	public StAXMetadata() {
	}

	public StAXMetadata(String name, String author, String date,
			String description, String format, String history) {
		this();

		this.name = name;
		this.author = author;
		this.date = date;
		this.description = description;
		this.format = format;
		this.history = history;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof StAXMetadata))
			return false;

		StAXMetadata other = (StAXMetadata)o;

		return
		(name != null ? name.equals(other.name) : other.name == null) &&
		(author != null ? author.equals(other.author) : other.author == null) &&
		(date != null ? date.equals(other.date) : other.date == null) &&
		(description != null ? description.equals(other.description) : other.description == null) &&
		(format != null ? format.equals(other.format) : other.format == null) &&
		(history != null ? history.equals(other.history) : other.history == null);
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode += name != null ? name.hashCode() : 0;
		hashCode *= 3;
		hashCode += author != null ? author.hashCode() : 0;
		hashCode *= 5;
		hashCode += date != null ? date.hashCode() : 0;
		hashCode *= 7;
		hashCode += description != null ? description.hashCode() : 0;
		hashCode *= 11;
		hashCode += format != null ? format.hashCode() : 0;
		hashCode *= 13;
		hashCode += history != null ? history.hashCode() : 0;

		return hashCode;
	}

	@Override
	public String toString() {
		return new Formatter().format("{name=%s, author=%s, date=%s, description=%s, format=%s, history=%s",
				name, author, date, description, format, history).toString();
	}


}
