package br.eti.rslemos.tagger;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.HashMap;

import org.testng.annotations.Test;

import br.eti.rslemos.tagger.LookupTokenTagger;
import br.eti.rslemos.tagger.Token;

public class LookupTokenTaggerBehavior {
	@Test
	public void shouldNotTagToken() {
		Token token = mock(Token.class);
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Tag)new DefaultTag("bar")));
		tagger.tag(token);
		
		verify(token, never()).setTag(anyTag());
	}

	@Test
	public void shouldTagToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Tag)new DefaultTag("bar")));
		tagger.tag(token);
		
		verify(token).setTag(new DefaultTag("bar"));
	}

	@Test
	public void shouldNullTagToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Tag)null));
		tagger.tag(token);
		
		verify(token).setTag(null);
	}

	@Test
	public void shouldCorreclyTagTokens() {
		Token token1 = mock(Token.class);
		when(token1.getWord()).thenReturn("foo");

		Token token2 = mock(Token.class);
		when(token2.getWord()).thenReturn("bar");
		
		HashMap<String, Tag> lexicon = new HashMap<String, Tag>();
		lexicon.put("foo", new DefaultTag("tag-foo"));
		lexicon.put("bar", new DefaultTag("tag-bar"));
		
		LookupTokenTagger tagger = new LookupTokenTagger(lexicon);

		tagger.tag(token1);
		verify(token1).setTag(new DefaultTag("tag-foo"));

		tagger.tag(token2);
		verify(token2).setTag(new DefaultTag("tag-bar"));
	}

	private static Tag anyTag() {
		return anyObject();
	}
}
