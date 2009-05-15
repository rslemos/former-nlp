package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;

public class RBIGRAMRuleBehavior {
	@Test
	public void shouldNotMatchBecauseOfWordMismatch() {
		Token next = mock(Token.class);
		when(next.getWord()).thenReturn("next-word");
		
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");
		
		Context context = new Context(new Token[] { token, next });
		
		Rule rule = new RBIGRAMRule("foo", "bar", "this-word", "next-word");
		assertFalse(rule.matches(context));
	}

	@Test
	public void shouldNotMatchBecauseOfRightWordMismatch() {
		Token next = mock(Token.class);
		
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");
		when(token.getWord()).thenReturn("this-word");
		
		Context context = new Context(new Token[] { token, next });
		
		Rule rule = new RBIGRAMRule("foo", "bar", "this-word", "next-word");
		assertFalse(rule.matches(context));
	}

	@Test
	public void shouldMatch() {
		Token next = mock(Token.class);
		when(next.getWord()).thenReturn("next-word");
		
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");
		when(token.getWord()).thenReturn("this-word");
		
		Context context = new Context(new Token[] { token, next });
		
		Rule rule = new RBIGRAMRule("foo", "bar", "this-word", "next-word");
		assertTrue(rule.matches(context));
	}
}
