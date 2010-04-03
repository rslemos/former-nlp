package br.eti.rslemos.tagger;

import static org.mockito.Mockito.*;
import org.testng.annotations.Test;

import br.eti.rslemos.tagger.ConstantTokenTagger;
import br.eti.rslemos.tagger.Token;

public class ConstantTokenTaggerBehavior {
	@Test
	public void shouldTag() {
		Token token = mock(Token.class);
		
		ConstantTokenTagger tagger = new ConstantTokenTagger(new DefaultTag("CT"));
		tagger.tag(token);
		
		verify(token).setTag(new DefaultTag("CT"));
	}
}
