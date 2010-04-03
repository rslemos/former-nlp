package br.eti.rslemos.brill;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;

import org.mockito.InOrder;
import org.testng.annotations.Test;

import br.eti.rslemos.tagger.AbstractTokenTagger;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultTag;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tag;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerBehavior {

	@Test
	public void shouldWorkOnEmptyInput() {
		BrillTagger tagger = new BrillTagger();
		
		tagger.tag(newDefaultSentence());
	}

	@Test
	public void shouldInvokeBaseTaggerAndTagToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");

		Tagger baseTagger = new AbstractTokenTagger() {
			public void tag(Token token) {
				assertEquals(token.getWord(), "foo");
				token.setTag(new DefaultTag("foobar"));
			}
		};

		BrillTagger tagger = new BrillTagger(baseTagger);
		
		tagger.tag(newDefaultSentence(token));

		verify(token, times(1)).setTag(new DefaultTag("foobar"));
	}

	@Test
	public void shouldInvokeBaseTaggerAndRuleAndTagToken() {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("foo");
		when(token.getTag()).thenReturn(new DefaultTag("bar"));

		Rule rule = new RuleAdapter() {
			@Override
			public boolean apply(Context context) {
				Token token = context.getToken(0);
				assertEquals(token.getWord(), "foo");
				assertEquals(token.getTag(), new DefaultTag("bar"));
				token.setTag(new DefaultTag("foobar"));
				
				return true;
			}
		};
		
		Tagger baseTagger = mock(Tagger.class);
		
		BrillTagger tagger = new BrillTagger(baseTagger, Arrays.asList(rule));
		
		tagger.tag(newDefaultSentence(token));

		InOrder inOrder = inOrder(baseTagger, token);
		inOrder.verify(baseTagger, times(1)).tag(anySentence());
		inOrder.verify(token, times(1)).setTag(new DefaultTag("foobar"));
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
				verify(token2, never()).setTag(anyTag());
				verify(token1, never()).setTag(anyTag());
				
				Token token = context.getToken(0);
				token.setTag(new DefaultTag("foobar"));
				
				return true;
			}
		};

		BrillTagger tagger = new BrillTagger(baseTagger, Arrays.asList(rule));
		
		tagger.tag(newDefaultSentence(token1, token2));

		verify(token1, times(1)).setTag(new DefaultTag("foobar"));
		verify(token2, times(1)).setTag(new DefaultTag("foobar"));
	}

	private static class RuleAdapter implements Rule {
		public Tag getFrom() {
			return null;
		}

		public Tag getTo() {
			return null;
		}

		public boolean matches(Context context) {
			return false;
		}

		public boolean apply(Context context) {
			return false;
		}

		public boolean firingDependsOnTag(Tag tag) {
			return false;
		}
	}

	private static Sentence anySentence() {
		return anyObject();
	}

	private static Tag anyTag() {
		return anyObject();
	}
	
	private DefaultSentence newDefaultSentence(Token... tokens) {
		return new DefaultSentence(Arrays.asList(tokens));
	}
	
}
