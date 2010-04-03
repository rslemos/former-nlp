package br.eti.rslemos.tagger;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.HashMap;

import org.testng.annotations.Test;

import br.eti.rslemos.tagger.LookupTokenTagger;
import br.eti.rslemos.tagger.Token;

public class LookupTokenTaggerBehavior {
	@Test
	public void shouldNotObjectToken() {
		Token token = mock(Token.class);
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Object)"bar"));
		tagger.tag(token);
		
		verify(token, never()).setTag(anyObject());
	}

	@Test
	public void shouldObjectToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Object)"bar"));
		tagger.tag(token);
		
		verify(token).setTag("bar");
	}

	@Test
	public void shouldNullObjectToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Object)null));
		tagger.tag(token);
		
		verify(token).setTag(null);
	}

	@Test
	public void shouldCorreclyObjectTokens() {
		Token token1 = mock(Token.class);
		when(token1.getWord()).thenReturn("foo");

		Token token2 = mock(Token.class);
		when(token2.getWord()).thenReturn("bar");
		
		HashMap<String, Object> lexicon = new HashMap<String, Object>();
		lexicon.put("foo", "tag-foo");
		lexicon.put("bar", "tag-bar");
		
		LookupTokenTagger tagger = new LookupTokenTagger(lexicon);

		tagger.tag(token1);
		verify(token1).setTag("tag-foo");

		tagger.tag(token2);
		verify(token2).setTag("tag-bar");
	}
}
