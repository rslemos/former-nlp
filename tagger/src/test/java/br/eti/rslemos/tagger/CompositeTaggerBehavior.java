package br.eti.rslemos.tagger;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.mockito.InOrder;
import org.junit.Test;

public class CompositeTaggerBehavior {
	
	@Test
	public void shouldTransferTokenProperties() {
		Token token = mock(Token.class);
		when(token.getFeature(AbstractToken.WORD)).thenReturn("foo");
		when(token.getFeature(AbstractToken.POS)).thenReturn("bar");
		
		Tagger subTagger = new AbstractTokenTagger() {
			@Override
			public void tag(Token token) {
				assertEquals(token.getFeature(AbstractToken.WORD), "foo");
				assertEquals(token.getFeature(AbstractToken.POS), "bar");
			}
		};
		
		tagToken(buildTagger(subTagger), token);
	}

	@Test
	public void shouldNotObjectToken() {
		Token token = mock(Token.class);
		
		tagToken(this.buildTagger(), token);

		verify(token, never()).setFeature(same(AbstractToken.POS), anyObject());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndStillNotObjectToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = mock(Tagger.class);
		Tagger subTagger3 = mock(Tagger.class);
		
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		InOrder inOrder = inOrder(subTagger1, subTagger2, subTagger3);
		
		inOrder.verify(subTagger1).tag(anySentence());
		inOrder.verify(subTagger2).tag(anySentence());
		inOrder.verify(subTagger3).tag(anySentence());
		
		verify(token, never()).setFeature(same(AbstractToken.POS), anyObject());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndObjectToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = new AbstractTokenTagger() {
			@Override
			public void tag(Token token) {
				token.setFeature(AbstractToken.POS, "foobar");
			}
		};
		Tagger subTagger3 = mock(Tagger.class);
		
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		verify(subTagger1).tag(anySentence());
		verify(subTagger3).tag(anySentence());
		
		verify(token).setFeature(AbstractToken.POS, "foobar");
	}

	@Test
	public void shouldInvokeAllSubTaggersAndIgnoreObjectOnJustObjectgedToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = new AbstractTokenTagger() {
			@Override
			public void tag(Token token) {
				token.setFeature(AbstractToken.POS, "foobar");
			}
		};

		final boolean[] check = { false };
		Tagger subTagger3 = new AbstractTokenTagger() {
			@Override
			public void tag(Token token) {
				check[0] = true;
				token.setFeature(AbstractToken.POS, "foobar");
			}
		};
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		assertTrue(check[0]);
		
		verify(subTagger1).tag(anySentence());
		verify(token, times(1)).setFeature(AbstractToken.POS, "foobar");
	}

	private void tagToken(CompositeTagger tagger, Token token) {
		tagger.tag(new DefaultSentence(Collections.singletonList(token)));
	}

	private CompositeTagger buildTagger(Tagger... taggers) {
		return new CompositeTagger(taggers);
	}

	private static Sentence anySentence() {
		return anyObject();
	}
}
