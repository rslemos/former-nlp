package br.eti.rslemos.brill;

import static org.mockito.Mockito.*;
import org.testng.annotations.Test;

public class ConstantTokenTaggerBehavior {
	@Test
	public void shouldTag() {
		Token token = mock(Token.class);
		
		ConstantTokenTagger tagger = new ConstantTokenTagger("CT");
		tagger.tag(token);
		
		verify(token).setTag("CT");
	}
}
