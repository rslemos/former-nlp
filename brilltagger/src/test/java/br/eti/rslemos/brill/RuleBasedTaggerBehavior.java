package br.eti.rslemos.brill;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

import org.mockito.InOrder;
import org.testng.annotations.Test;

import br.eti.rslemos.tagger.AbstractTokenTagger;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class RuleBasedTaggerBehavior {

	@Test
	public void shouldWorkOnEmptyInput() {
		RuleBasedTagger tagger = new RuleBasedTagger();
		
		tagger.tag(new DefaultSentence());
	}
	
	@Test
	public void shouldInvokeBaseTaggerAndTagToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");

		Tagger baseTagger = new AbstractTokenTagger() {
			public void tag(Token token) {
				assertEquals(token.getWord(), "foo");
				token.setTag("foobar");
			}
		};

		RuleBasedTagger tagger = new RuleBasedTagger(baseTagger);
		
		tagger.tag(new DefaultSentence(token));

		verify(token, times(1)).setTag("foobar");
	}

	@Test
	public void shouldInvokeBaseTaggerAndRuleAndTagToken() {
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
		
		RuleBasedTagger tagger = new RuleBasedTagger(baseTagger, rule);
		
		tagger.tag(new DefaultSentence(token));

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
		
		RuleBasedTagger tagger = new RuleBasedTagger(baseTagger, rule1, rule2);
		
		tagger.tag(new DefaultSentence(token));

		InOrder inOrder = inOrder(baseTagger, rule1, rule2);
		inOrder.verify(baseTagger, times(1)).tag(anySentence());
		inOrder.verify(rule1, times(1)).apply((Context) anyObject());
		inOrder.verify(rule2, times(1)).apply((Context) anyObject());
	}

	@Test
	public void shouldIsolateRuleEffects() {
		final Token token1 = mock(Token.class);
		final Token token2 = mock(Token.class);

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

		RuleBasedTagger tagger = new RuleBasedTagger(baseTagger, rule);
		
		tagger.tag(new DefaultSentence(token1, token2));

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

		public boolean firingDependsOnTag(String tag) {
			return false;
		}
	}

	private static Sentence anySentence() {
		return (Sentence) anyObject();
	}
}
