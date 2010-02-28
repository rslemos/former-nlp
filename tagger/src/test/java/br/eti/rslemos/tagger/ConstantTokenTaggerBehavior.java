package br.eti.rslemos.tagger;

import static org.mockito.Mockito.*;
import org.testng.annotations.Test;

import br.eti.rslemos.tagger.ConstantTokenTagger;
import br.eti.rslemos.tagger.Token;

@SuppressWarnings("unchecked")
public class ConstantTokenTaggerBehavior {
	@Test
	public void shouldTag() {
		Token<String> token = mock(Token.class);
		
		ConstantTokenTagger<String> tagger = new ConstantTokenTagger<String>("CT");
		tagger.tag(token);
		
		verify(token).setTag("CT");
	}
}
