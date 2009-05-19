package br.eti.rslemos.brill;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InOrder;
import org.testng.annotations.Test;

public class CompositeTaggerBehavior {
	
	@Test
	public void shouldTransferTokenProperties() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		when(token.getTag()).thenReturn("bar");
		
		Tagger subTagger = new AbstractTokenTagger() {
			public void tag(Token token) {
				assertEquals(token.getWord(), "foo");
				assertEquals(token.getTag(), "bar");
			}
		};
		
		List<Tagger> taggers = Arrays.asList(subTagger);
		buildTaggerAndTagSentence(taggers, token);
	}

	@Test
	public void shouldNotTagToken() {
		Token token = mock(Token.class);
		
		List<Tagger> taggers = Collections.emptyList();
		
		buildTaggerAndTagSentence(taggers, token);
		
		verify(token, never()).setTag(anyString());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndStillNotTagToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = mock(Tagger.class);
		Tagger subTagger3 = mock(Tagger.class);
		
		List<Tagger> taggers = Arrays.asList(subTagger1, subTagger2, subTagger3);
		buildTaggerAndTagSentence(taggers, token);
		
		InOrder inOrder = inOrder(subTagger1, subTagger2, subTagger3);
		
		inOrder.verify(subTagger1).tagSentence(anyTokenList());
		inOrder.verify(subTagger2).tagSentence(anyTokenList());
		inOrder.verify(subTagger3).tagSentence(anyTokenList());
		
		verify(token, never()).setTag(anyString());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndTagToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = new AbstractTokenTagger() {
			public void tag(Token token) {
				token.setTag("foobar");
			}
		};
		Tagger subTagger3 = mock(Tagger.class);
		
		List<Tagger> taggers = Arrays.asList(subTagger1, subTagger2, subTagger3);
		buildTaggerAndTagSentence(taggers, token);
		
		verify(subTagger1).tagSentence(anyTokenList());
		verify(subTagger3).tagSentence(anyTokenList());
		
		verify(token).setTag("foobar");
	}

	@Test
	public void shouldInvokeAllSubTaggersAndIgnoreTagOnJustTaggedToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = new AbstractTokenTagger() {
			public void tag(Token token) {
				token.setTag("foobar");
			}
		};

		final boolean[] check = { false };
		Tagger subTagger3 = new AbstractTokenTagger() {
			public void tag(Token token) {
				check[0] = true;
				token.setTag("foobar");
			}
		};
		
		List<Tagger> taggers = Arrays.asList(subTagger1, subTagger2, subTagger3);
		buildTaggerAndTagSentence(taggers, token);
		
		assertTrue(check[0]);
		
		verify(subTagger1).tagSentence(anyTokenList());
		verify(token, times(1)).setTag("foobar");
	}

	private void buildTaggerAndTagSentence(List<Tagger> taggers, Token token) {
		new CompositeTagger(taggers).tagSentence(Arrays.asList(token));
	}

	@SuppressWarnings("unchecked")
	private List<Token> anyTokenList() {
		return (List<Token>) anyObject();
	}

}
