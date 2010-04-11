package br.eti.rslemos.brill;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;

import org.mockito.InOrder;
import org.testng.annotations.Test;

import br.eti.rslemos.tagger.AbstractTokenTagger;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerBehavior {

	@Test
	public void shouldWorkOnEmptyInput() {
		BrillTagger tagger = new BrillTagger();
		
		tagger.tag(newDefaultSentence());
	}

	@Test
	public void shouldInvokeBaseTaggerAndObjectToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");

		Tagger baseTagger = new AbstractTokenTagger() {
			@Override
			public void tag(Token token) {
				assertEquals(token.getWord(), "foo");
				token.setTag("foobar");
			}
		};

		BrillTagger tagger = new BrillTagger(baseTagger);
		
		tagger.tag(newDefaultSentence(token));

		verify(token, times(1)).setTag("foobar");
	}

	@Test
	public void shouldInvokeBaseTaggerAndRuleAndObjectToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		when(token.getTag()).thenReturn("bar");

		Rule rule = new RuleAdapter() {
			@Override
			public boolean apply(Context context) {
				Token token = context.getToken(0);
				assertEquals(token.getWord(), "foo");
				assertEquals(token.getTag(), "bar");
				token.setTag("foobar");
				
				return true;
			}
		};
		
		Tagger baseTagger = mock(Tagger.class);
		
		BrillTagger tagger = new BrillTagger(baseTagger, Arrays.asList(rule));
		
		tagger.tag(newDefaultSentence(token));

		InOrder inOrder = inOrder(baseTagger, token);
		inOrder.verify(baseTagger, times(1)).tag(anySentence());
		inOrder.verify(token, times(1)).setTag("foobar");
	}

	@Test
	public void shouldInvokeBaseTaggerAndRulesInOrder() {
		Token token = mock(Token.class);

		Tagger baseTagger = mock(Tagger.class);

		Rule rule1 = mock(Rule.class);
		Rule rule2 = mock(Rule.class);
		
		BrillTagger tagger = new BrillTagger(baseTagger, Arrays.asList(rule1, rule2));
		
		tagger.tag(newDefaultSentence(token));

		InOrder inOrder = inOrder(baseTagger, rule1, rule2);
		inOrder.verify(baseTagger, times(1)).tag(anySentence());
		inOrder.verify(rule1, times(1)).apply(anyContext());
		inOrder.verify(rule2, times(1)).apply(anyContext());
	}

	private Context anyContext() {
		return (Context) anyObject();
	}

	@Test
	public void shouldIsolateRuleEffects() {
		final Token token1 = mock(Token.class);
		final Token token2 = mock(Token.class);

		Tagger baseTagger = mock(Tagger.class);

		Rule rule = new RuleAdapter() {
			@Override
			public boolean apply(Context context) {
				verify(token2, never()).setTag(anyObject());
				verify(token1, never()).setTag(anyObject());
				
				Token token = context.getToken(0);
				token.setTag("foobar");
				
				return true;
			}
		};

		BrillTagger tagger = new BrillTagger(baseTagger, Arrays.asList(rule));
		
		tagger.tag(newDefaultSentence(token1, token2));

		verify(token1, times(1)).setTag("foobar");
		verify(token2, times(1)).setTag("foobar");
	}

	private static class RuleAdapter implements Rule {
		public Object getFrom() {
			return null;
		}

		public Object getTo() {
			return null;
		}

		public boolean matches(Context context) {
			return false;
		}

		public boolean apply(Context context) {
			return false;
		}

		public boolean firingDependsOnObject(Object tag) {
			return false;
		}
	}

	private static Sentence anySentence() {
		return anyObject();
	}

	private DefaultSentence newDefaultSentence(Token... tokens) {
		return new DefaultSentence(Arrays.asList(tokens));
	}
	
}
