package br.eti.rslemos.brill;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InOrder;
import org.testng.annotations.Test;

public class RuleBasedTaggerBehavior {

	@Test
	public void shouldWorkOnEmptyInput() {
		RuleBasedTagger tagger = new RuleBasedTagger();
		
		List<Token> sentence = Collections.emptyList();
		tagger.tagSentence(sentence);
	}
	
	@Test
	public void shouldInvokeBaseTaggerAndTagToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");

		List<Token> sentence = Arrays.asList(token);

		Tagger baseTagger = new AbstractTokenTagger() {
			public void tag(Token token) {
				assertEquals(token.getWord(), "foo");
				token.setTag("foobar");
			}
		};

		RuleBasedTagger tagger = new RuleBasedTagger(baseTagger);
		
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

		Tagger baseTagger = mock(Tagger.class);
		
		RuleBasedTagger tagger = new RuleBasedTagger(baseTagger, rules);
		
		tagger.tagSentence(sentence);

		InOrder inOrder = inOrder(baseTagger, token);
		inOrder.verify(baseTagger, times(1)).tagSentence(anyTokenList());
		inOrder.verify(token, times(1)).setTag("foobar");
	}

	@Test
	public void shouldInvokeBaseTaggerAndRulesInOrder() {
		Token token = mock(Token.class);

		List<Token> sentence = Arrays.asList(token);

		Tagger baseTagger = mock(Tagger.class);

		Rule rule1 = mock(Rule.class);
		Rule rule2 = mock(Rule.class);
		List<Rule> rules = Arrays.asList(rule1, rule2);

		
		RuleBasedTagger tagger = new RuleBasedTagger(baseTagger, rules);
		
		tagger.tagSentence(sentence);

		InOrder inOrder = inOrder(baseTagger, rule1, rule2);
		inOrder.verify(baseTagger, times(1)).tagSentence(anyTokenList());
		inOrder.verify(rule1, times(1)).apply((Context) anyObject());
		inOrder.verify(rule2, times(1)).apply((Context) anyObject());
	}

	@Test
	public void shouldIsolateRuleEffects() {
		final Token token1 = mock(Token.class);
		final Token token2 = mock(Token.class);

		List<Token> sentence = Arrays.asList(token1, token2);

		Tagger baseTagger = mock(Tagger.class);

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

		RuleBasedTagger tagger = new RuleBasedTagger(baseTagger, rules);
		
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

	@SuppressWarnings("unchecked")
	private List<Token> anyTokenList() {
		return (List<Token>) anyObject();
	}
}
