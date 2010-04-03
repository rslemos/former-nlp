package br.eti.rslemos.tagger;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Collections;

import org.mockito.InOrder;
import org.testng.annotations.Test;

public class CompositeTaggerBehavior {
	
	@Test
	public void shouldTransferTokenProperties() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		when(token.getTag()).thenReturn(new DefaultTag("bar"));
		
		Tagger subTagger = new AbstractTokenTagger() {
			public void tag(Token token) {
				assertEquals(token.getWord(), "foo");
				assertEquals(token.getTag(), new DefaultTag("bar"));
			}
		};
		
		tagToken(buildTagger(subTagger), token);
	}

	@Test
	public void shouldNotTagToken() {
		Token token = mock(Token.class);
		
		tagToken(this.buildTagger(), token);
		
		verify(token, never()).setTag(anyTag());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndStillNotTagToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = mock(Tagger.class);
		Tagger subTagger3 = mock(Tagger.class);
		
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		InOrder inOrder = inOrder(subTagger1, subTagger2, subTagger3);
		
		inOrder.verify(subTagger1).tag(anySentence());
		inOrder.verify(subTagger2).tag(anySentence());
		inOrder.verify(subTagger3).tag(anySentence());
		
		verify(token, never()).setTag(anyTag());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndTagToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = new AbstractTokenTagger() {
			public void tag(Token token) {
				token.setTag(new DefaultTag("foobar"));
			}
		};
		Tagger subTagger3 = mock(Tagger.class);
		
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		verify(subTagger1).tag(anySentence());
		verify(subTagger3).tag(anySentence());
		
		verify(token).setTag(new DefaultTag("foobar"));
	}

	@Test
	public void shouldInvokeAllSubTaggersAndIgnoreTagOnJustTaggedToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = new AbstractTokenTagger() {
			public void tag(Token token) {
				token.setTag(new DefaultTag("foobar"));
			}
		};

		final boolean[] check = { false };
		Tagger subTagger3 = new AbstractTokenTagger() {
			public void tag(Token token) {
				check[0] = true;
				token.setTag(new DefaultTag("foobar"));
			}
		};
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		assertTrue(check[0]);
		
		verify(subTagger1).tag(anySentence());
		verify(token, times(1)).setTag(new DefaultTag("foobar"));
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

	private static Tag anyTag() {
		return anyObject();
	}

}
