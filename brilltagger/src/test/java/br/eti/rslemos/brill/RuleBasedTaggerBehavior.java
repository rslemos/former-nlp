package br.eti.rslemos.brill;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
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

@SuppressWarnings("unchecked")
public class RuleBasedTaggerBehavior {

	@Test
	public void shouldWorkOnEmptyInput() {
		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>();
		
		tagger.tag(new DefaultSentence<String>());
	}
	
	@Test
	public void shouldInvokeBaseTaggerAndTagToken() {
		Token<String> token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");

		Tagger<String> baseTagger = new AbstractTokenTagger<String>() {
			public void tag(Token<String> token) {
				assertEquals(token.getWord(), "foo");
				token.setTag("foobar");
			}
		};

		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>(baseTagger);
		
		tagger.tag(new DefaultSentence<String>(token));

		verify(token, times(1)).setTag("foobar");
	}

	@Test
	public void shouldInvokeBaseTaggerAndRuleAndTagToken() {
		Token<String> token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		when(token.getTag()).thenReturn("bar");

		Rule<String> rule = new RuleAdapter<String>() {
			@Override
			public boolean apply(Context<String> context) {
				Token<String> token = context.getToken(0);
				assertEquals(token.getWord(), "foo");
				assertEquals(token.getTag(), "bar");
				token.setTag("foobar");
				
				return true;
			}
		};
		
		Tagger<String> baseTagger = mock(Tagger.class);
		
		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>(baseTagger, Arrays.asList(rule));
		
		tagger.tag(new DefaultSentence<String>(token));

		InOrder inOrder = inOrder(baseTagger, token);
		inOrder.verify(baseTagger, times(1)).tag(anySentence());
		inOrder.verify(token, times(1)).setTag("foobar");
	}

	@Test
	public void shouldInvokeBaseTaggerAndRulesInOrder() {
		Token<String> token = mock(Token.class);

		Tagger<String> baseTagger = mock(Tagger.class);

		Rule<String> rule1 = mock(Rule.class);
		Rule<String> rule2 = mock(Rule.class);
		
		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>(baseTagger, Arrays.asList(rule1, rule2));
		
		tagger.tag(new DefaultSentence<String>(token));

		InOrder inOrder = inOrder(baseTagger, rule1, rule2);
		inOrder.verify(baseTagger, times(1)).tag(anySentence());
		inOrder.verify(rule1, times(1)).apply(anyContext());
		inOrder.verify(rule2, times(1)).apply(anyContext());
	}

	private Context<String> anyContext() {
		return (Context<String>) anyObject();
	}

	@Test
	public void shouldIsolateRuleEffects() {
		final Token<String> token1 = mock(Token.class);
		final Token<String> token2 = mock(Token.class);

		Tagger<String> baseTagger = mock(Tagger.class);

		Rule<String> rule = new RuleAdapter<String>() {
			@Override
			public boolean apply(Context<String> context) {
				verify(token2, never()).setTag(anyString());
				verify(token1, never()).setTag(anyString());
				
				Token<String> token = context.getToken(0);
				token.setTag("foobar");
				
				return true;
			}
		};

		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>(baseTagger, Arrays.asList(rule));
		
		tagger.tag(new DefaultSentence<String>(token1, token2));

		verify(token1, times(1)).setTag("foobar");
		verify(token2, times(1)).setTag("foobar");
	}

	private static class RuleAdapter<T1> implements Rule<T1> {
		public T1 getFrom() {
			return null;
		}

		public T1 getTo() {
			return null;
		}

		public boolean matches(Context<T1> context) {
			return false;
		}

		public boolean apply(Context<T1> context) {
			return false;
		}

		public boolean firingDependsOnTag(T1 tag) {
			return false;
		}
	}

	private static Sentence<String> anySentence() {
		return (Sentence<String>) anyObject();
	}
}
