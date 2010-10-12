package br.eti.rslemos.tiger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.xml.sax.InputSource;

public class TigerInputMotherMetaBehavior {

	@Test
	public void shouldValidateStraightTiger() throws Throwable {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setSchema(TigerSchema.getSchema());

		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		documentBuilder.parse(new InputSource(TigerInputMother.getInputStraightTiger()));

	}
}

