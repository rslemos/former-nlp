package br.eti.rslemos.tagger;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
		Tagger[] taggers = { subTagger };
		
		tagToken(buildTagger(taggers), token);
	}

	@Test
	public void shouldNotTagToken() {
		Token token = mock(Token.class);
		Tagger[] taggers = {};
		
		tagToken(buildTagger(taggers), token);
		
		verify(token, never()).setTag(anyString());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndStillNotTagToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = mock(Tagger.class);
		Tagger subTagger3 = mock(Tagger.class);
		Tagger[] taggers = { subTagger1, subTagger2, subTagger3 };
		
		tagToken(buildTagger(taggers), token);
		
		InOrder inOrder = inOrder(subTagger1, subTagger2, subTagger3);
		
		inOrder.verify(subTagger1).tag(anySentence());
		inOrder.verify(subTagger2).tag(anySentence());
		inOrder.verify(subTagger3).tag(anySentence());
		
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
		Tagger[] taggers = { subTagger1, subTagger2, subTagger3 };
		
		tagToken(buildTagger(taggers), token);
		
		verify(subTagger1).tag(anySentence());
		verify(subTagger3).tag(anySentence());
		
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
		Tagger[] taggers = { subTagger1, subTagger2, subTagger3 };
		
		tagToken(buildTagger(taggers), token);
		
		assertTrue(check[0]);
		
		verify(subTagger1).tag(anySentence());
		verify(token, times(1)).setTag("foobar");
	}

	private void tagToken(CompositeTagger tagger, Token token) {
		tagger.tag(new DefaultSentence(token));
	}

	private CompositeTagger buildTagger(Tagger... taggers) {
		return new CompositeTagger(taggers);
	}

	private Sentence anySentence() {
		return (Sentence) anyObject();
	}

}
