package br.eti.rslemos.ad;

import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.testng.annotations.Test;

public class ADParserBehavior {
	
	@Test
	public void shouldParseExt_1000() throws Throwable {
		InputStream ext_1000 = ADParserBehavior.class.getResourceAsStream("ext_1000.ad");
		ANTLRInputStream input = new ANTLRInputStream(ext_1000);
        ADLexer lexer = new ADLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ADParser parser = new ADParser(tokens);
        parser.bosque();
	}
}
