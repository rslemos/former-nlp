package br.eti.rslemos.tagger;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.HashMap;

import org.testng.annotations.Test;

import br.eti.rslemos.tagger.LookupTokenTagger;
import br.eti.rslemos.tagger.Token;

@SuppressWarnings("unchecked")
public class LookupTokenTaggerBehavior {
	@Test
	public void shouldNotTagToken() {
		Token<String> token = mock(Token.class);
		
		LookupTokenTagger<String> tagger = new LookupTokenTagger<String>(Collections.singletonMap("foo", "bar"));
		tagger.tag(token);
		
		verify(token, never()).setTag(anyString());
	}

	@Test
	public void shouldTagToken() {
		Token<String> token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		
		LookupTokenTagger<String> tagger = new LookupTokenTagger<String>(Collections.singletonMap("foo", "bar"));
		tagger.tag(token);
		
		verify(token).setTag("bar");
	}

	@Test
	public void shouldNullTagToken() {
		Token<String> token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		
		LookupTokenTagger<String> tagger = new LookupTokenTagger<String>(Collections.singletonMap("foo", (String)null));
		tagger.tag(token);
		
		verify(token).setTag(null);
	}

	@Test
	public void shouldCorreclyTagTokens() {
		Token<String> token1 = mock(Token.class);
		when(token1.getWord()).thenReturn("foo");

		Token<String> token2 = mock(Token.class);
		when(token2.getWord()).thenReturn("bar");
		
		HashMap<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("foo", "tag-foo");
		lexicon.put("bar", "tag-bar");
		
		LookupTokenTagger<String> tagger = new LookupTokenTagger<String>(lexicon);

		tagger.tag(token1);
		verify(token1).setTag("tag-foo");

		tagger.tag(token2);
		verify(token2).setTag("tag-bar");
	}

}
