package br.eti.rslemos.brill;

import static org.mockito.Mockito.*;
import org.testng.annotations.Test;

public class ConstantBaseTaggerBehavior {
	@Test
	public void shouldTag() {
		Token token = mock(Token.class);
		
		BaseTagger tagger = new ConstantBaseTagger("CT");
		tagger.tag(token);
		
		verify(token).setTag("CT");
	}
}
