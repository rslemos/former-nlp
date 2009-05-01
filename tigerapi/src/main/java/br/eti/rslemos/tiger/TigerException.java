package br.eti.rslemos.tiger;

import javax.xml.stream.XMLStreamException;

public class TigerException extends Exception {

	private static final long serialVersionUID = -1814827932980996458L;

	public TigerException(String message, XMLStreamException cause) {
		super(message, cause);
	}

}
