package br.eti.rslemos.tagger;

import static org.mockito.Mockito.*;
import org.junit.Test;

import br.eti.rslemos.tagger.ConstantTokenTagger;
import br.eti.rslemos.tagger.Token;

public class ConstantTokenTaggerBehavior {
	@Test
	public void shouldObject() {
		Token token = mock(Token.class);
		
		ConstantTokenTagger tagger = new ConstantTokenTagger("CT");
		tagger.tag(token);
		
		verify(token).setFeature(Token.POS, "CT");
	}
}
