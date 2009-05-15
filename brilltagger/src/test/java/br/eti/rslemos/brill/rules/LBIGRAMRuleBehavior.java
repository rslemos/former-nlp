package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;

public class LBIGRAMRuleBehavior {
	@Test
	public void shouldNotMatchBecauseOfWordMismatch() {
		Token prev = mock(Token.class);
		when(prev.getWord()).thenReturn("prev-word");
		
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");
		
		Context context = new Context(new Token[] { prev, token });
		context.advance();
		
		Rule rule = new LBIGRAMRule("foo", "bar", "prev-word", "this-word");
		assertFalse(rule.matches(context));
	}

	@Test
	public void shouldNotMatchBecauseOfLeftWordMismatch() {
		Token prev = mock(Token.class);
		
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");
		when(token.getWord()).thenReturn("this-word");
		
		Context context = new Context(new Token[] { prev, token });
		context.advance();
		
		Rule rule = new LBIGRAMRule("foo", "bar", "prev-word", "this-word");
		assertFalse(rule.matches(context));
	}

	@Test
	public void shouldMatch() {
		Token prev = mock(Token.class);
		when(prev.getWord()).thenReturn("prev-word");
		
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");
		when(token.getWord()).thenReturn("this-word");
		
		Context context = new Context(new Token[] { prev, token });
		context.advance();
		
		Rule rule = new LBIGRAMRule("foo", "bar", "prev-word", "this-word");
		assertTrue(rule.matches(context));
	}
}