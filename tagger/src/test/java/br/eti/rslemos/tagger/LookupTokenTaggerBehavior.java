package br.eti.rslemos.tagger;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;

import org.junit.Test;

public class LookupTokenTaggerBehavior {
	@Test
	public void shouldNotObjectToken() {
		Token token = mock(Token.class);
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Object)"bar"));
		tagger.tag(token);
		
		verify(token, never()).setFeature(same(Token.POS), anyObject());
	}

	@Test
	public void shouldObjectToken() {
		Token token = mock(Token.class);
		when(token.getFeature(Token.WORD)).thenReturn("foo");
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Object)"bar"));
		tagger.tag(token);
		
		verify(token).setFeature(Token.POS, "bar");
	}

	@Test
	public void shouldNullObjectToken() {
		Token token = mock(Token.class);
		when(token.getFeature(Token.WORD)).thenReturn("foo");
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Object)null));
		tagger.tag(token);
		
		verify(token).setFeature(Token.POS, null);
	}

	@Test
	public void shouldCorreclyObjectTokens() {
		Token token1 = mock(Token.class);
		when(token1.getFeature(Token.WORD)).thenReturn("foo");

		Token token2 = mock(Token.class);
		when(token2.getFeature(Token.WORD)).thenReturn("bar");
		
		HashMap<String, Object> lexicon = new HashMap<String, Object>();
		lexicon.put("foo", "tag-foo");
		lexicon.put("bar", "tag-bar");
		
		LookupTokenTagger tagger = new LookupTokenTagger(lexicon);

		tagger.tag(token1);
		verify(token1).setFeature(Token.POS, "tag-foo");

		tagger.tag(token2);
		verify(token2).setFeature(Token.POS, "tag-bar");
	}
}
