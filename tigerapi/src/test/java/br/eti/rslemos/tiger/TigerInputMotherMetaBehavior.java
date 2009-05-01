package br.eti.rslemos.tiger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.annotations.Test;
import org.xml.sax.InputSource;

@Test(groups = {"br.eti.rslemos.tiger", "meta.br.eti.rslemos.tiger"})
public class TigerInputMotherMetaBehavior {

	@Test
	public void shouldValidateStraightTiger() throws Throwable {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setSchema(TigerSchema.getSchema());

		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		documentBuilder.parse(new InputSource(TigerInputMother.getInputStraightTiger()));

	}
}

