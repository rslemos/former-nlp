package br.eti.rslemos.ad;

import java.io.InputStream;

import org.testng.annotations.Test;

public class ADParserBehavior {
	
	@Test
	public void shouldParseExt_1000() throws Throwable {
		InputStream ext_1000 = ADParserBehavior.class.getResourceAsStream("ext_1000.ad");
	}
}
