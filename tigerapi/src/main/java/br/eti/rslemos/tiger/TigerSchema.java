package br.eti.rslemos.tiger;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public final class TigerSchema {

	private TigerSchema() {
	}

	public static Schema getSchema() throws SAXException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		return schemaFactory.newSchema(TigerSchema.class.getClassLoader().getResource("schemata/TigerXML.xsd"));
	}

}