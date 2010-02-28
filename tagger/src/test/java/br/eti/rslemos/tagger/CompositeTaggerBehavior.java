package br.eti.rslemos.tagger;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.mockito.InOrder;
import org.testng.annotations.Test;

@SuppressWarnings("unchecked")
public class CompositeTaggerBehavior {
	
	@Test
	public void shouldTransferTokenProperties() {
		Token<String> token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		when(token.getTag()).thenReturn("bar");
		
		Tagger<String> subTagger = new AbstractTokenTagger<String>() {
			public void tag(Token<String> token) {
				assertEquals(token.getWord(), "foo");
				assertEquals(token.getTag(), "bar");
			}
		};
		
		tagToken(buildTagger(subTagger), token);
	}

	@Test
	public void shouldNotTagToken() {
		Token<String> token = mock(Token.class);
		
		tagToken(this.<String>buildTagger(), token);
		
		verify(token, never()).setTag(anyString());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndStillNotTagToken() {
		Token<String> token = mock(Token.class);
		
		Tagger<String> subTagger1 = mock(Tagger.class);
		Tagger<String> subTagger2 = mock(Tagger.class);
		Tagger<String> subTagger3 = mock(Tagger.class);
		
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		InOrder inOrder = inOrder(subTagger1, subTagger2, subTagger3);
		
		inOrder.verify(subTagger1).tag(anySentence());
		inOrder.verify(subTagger2).tag(anySentence());
		inOrder.verify(subTagger3).tag(anySentence());
		
		verify(token, never()).setTag(anyString());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndTagToken() {
		Token<String> token = mock(Token.class);
		
		Tagger<String> subTagger1 = mock(Tagger.class);
		Tagger<String> subTagger2 = new AbstractTokenTagger<String>() {
			public void tag(Token<String> token) {
				token.setTag("foobar");
			}
		};
		Tagger<String> subTagger3 = mock(Tagger.class);
		
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		verify(subTagger1).tag(anySentence());
		verify(subTagger3).tag(anySentence());
		
		verify(token).setTag("foobar");
	}

	@Test
	public void shouldInvokeAllSubTaggersAndIgnoreTagOnJustTaggedToken() {
		Token<String> token = mock(Token.class);
		
		Tagger<String> subTagger1 = mock(Tagger.class);
		Tagger<String> subTagger2 = new AbstractTokenTagger<String>() {
			public void tag(Token<String> token) {
				token.setTag("foobar");
			}
		};

		final boolean[] check = { false };
		Tagger<String> subTagger3 = new AbstractTokenTagger<String>() {
			public void tag(Token<String> token) {
				check[0] = true;
				token.setTag("foobar");
			}
		};
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		assertTrue(check[0]);
		
		verify(subTagger1).tag(anySentence());
		verify(token, times(1)).setTag("foobar");
	}

	private <T> void tagToken(CompositeTagger<T> tagger, Token<T> token) {
		tagger.tag(new DefaultSentence<T>(token));
	}

	private <T> CompositeTagger<T> buildTagger(Tagger<T>... taggers) {
		return new CompositeTagger<T>(taggers);
	}

	private Sentence<String> anySentence() {
		return (Sentence<String>) anyObject();
	}

}
