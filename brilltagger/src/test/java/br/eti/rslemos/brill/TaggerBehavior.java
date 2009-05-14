package br.eti.rslemos.brill;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InOrder;
import org.testng.annotations.Test;

public class TaggerBehavior {

	@Test
	public void shouldWorkOnEmptyInput() {
		Tagger tagger = new Tagger();
		
		List<Token> sentence = Collections.emptyList();
		tagger.tagSentence(sentence);
	}
	
	@Test
	public void shouldInvokeBaseTaggerAndTagToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");

		List<Token> sentence = Arrays.asList(token);

		BaseTagger baseTagger = new BaseTagger() {
			public void tag(Token token) {
				assertEquals(token.getWord(), "foo");
				token.setTag("foobar");
			}
		};

		Tagger tagger = new Tagger(baseTagger);
		
		tagger.tagSentence(sentence);

		verify(token, times(1)).setTag("foobar");
	}

	@Test
	public void shouldInvokeBaseTaggerAndRuleAndTagToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		when(token.getTag()).thenReturn("bar");

		List<Token> sentence = Arrays.asList(token);

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
		
		List<Rule> rules = Arrays.asList(rule);

		BaseTagger baseTagger = mock(BaseTagger.class);
		
		Tagger tagger = new Tagger(baseTagger, rules);
		
		tagger.tagSentence(sentence);

		InOrder inOrder = inOrder(baseTagger, token);
		inOrder.verify(baseTagger, times(1)).tag(token);
		inOrder.verify(token, times(1)).setTag("foobar");
	}

	@Test
	public void shouldInvokeBaseTaggerAndRulesInOrder() {
		Token token = mock(Token.class);

		List<Token> sentence = Arrays.asList(token);

		BaseTagger baseTagger = mock(BaseTagger.class);

		Rule rule1 = mock(Rule.class);
		Rule rule2 = mock(Rule.class);
		List<Rule> rules = Arrays.asList(rule1, rule2);

		
		Tagger tagger = new Tagger(baseTagger, rules);
		
		tagger.tagSentence(sentence);

		InOrder inOrder = inOrder(baseTagger, rule1, rule2);
		inOrder.verify(baseTagger, times(1)).tag(token);
		inOrder.verify(rule1, times(1)).apply((Context) anyObject());
		inOrder.verify(rule2, times(1)).apply((Context) anyObject());
	}

	@Test
	public void shouldIsolateRuleEffects() {
		final Token token1 = mock(Token.class);
		final Token token2 = mock(Token.class);

		List<Token> sentence = Arrays.asList(token1, token2);

		BaseTagger baseTagger = mock(BaseTagger.class);

		Rule rule = new RuleAdapter() {
			@Override
			public boolean apply(Context context) {
				verify(token2, never()).setTag(anyString());
				verify(token1, never()).setTag(anyString());
				
				Token token = context.getToken(0);
				token.setTag("foobar");
				
				return true;
			}
		};
		List<Rule> rules = Arrays.asList(rule);

		Tagger tagger = new Tagger(baseTagger, rules);
		
		tagger.tagSentence(sentence);

		verify(token1, times(1)).setTag("foobar");
		verify(token2, times(1)).setTag("foobar");
	}

	private static class RuleAdapter implements Rule {
		public String getFrom() {
			return null;
		}

		public String getTo() {
			return null;
		}

		public boolean matches(Context context) {
			return false;
		}

		public boolean apply(Context context) {
			return false;
		}
	}
}
