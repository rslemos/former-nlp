package br.eti.rslemos.brill;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InOrder;
import org.testng.annotations.Test;

public class CompositeBaseTaggerBehavior {
	
	@Test
	public void shouldNotTagToken() {
		Token token = mock(Token.class);
		
		List<BaseTagger> taggers = Collections.emptyList();
		
		BaseTagger tagger = new CompositeBaseTagger(taggers);
		tagger.tag(token);
		
		verify(token, never()).setTag(anyString());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndStillNotTagToken() {
		Token token = mock(Token.class);
		
		BaseTagger subTagger1 = mock(BaseTagger.class);
		BaseTagger subTagger2 = mock(BaseTagger.class);
		BaseTagger subTagger3 = mock(BaseTagger.class);
		
		List<BaseTagger> taggers = Arrays.asList(subTagger1, subTagger2, subTagger3);
		BaseTagger tagger = new CompositeBaseTagger(taggers);
		
		tagger.tag(token);
		
		InOrder inOrder = inOrder(subTagger1, subTagger2, subTagger3);
		
		inOrder.verify(subTagger1).tag((Token) anyObject());
		inOrder.verify(subTagger2).tag((Token) anyObject());
		inOrder.verify(subTagger3).tag((Token) anyObject());
		
		verify(token, never()).setTag(anyString());
	}

	@Test
	public void shouldInvokeTwoSubTaggersInOrderAndTagToken() {
		Token token = mock(Token.class);
		
		BaseTagger subTagger1 = mock(BaseTagger.class);
		BaseTagger subTagger2 = new BaseTagger() {
			public void tag(Token token) {
				token.setTag("foobar");
			}
		};
		BaseTagger subTagger3 = mock(BaseTagger.class);
		
		List<BaseTagger> taggers = Arrays.asList(subTagger1, subTagger2, subTagger3);
		BaseTagger tagger = new CompositeBaseTagger(taggers);
		
		tagger.tag(token);
		
		verify(subTagger1).tag((Token) anyObject());
		verify(subTagger3, never()).tag((Token) anyObject());
		
		verify(token).setTag("foobar");
	}

	@Test
	public void shouldTransferTokenProperties() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		when(token.getTag()).thenReturn("bar");
		
		BaseTagger subTagger = new BaseTagger() {
			public void tag(Token token) {
				assertEquals(token.getWord(), "foo");
				assertEquals(token.getTag(), "bar");
			}
		};
		
		List<BaseTagger> taggers = Arrays.asList(subTagger);
		BaseTagger tagger = new CompositeBaseTagger(taggers);
		
		tagger.tag(token);
	}
}
