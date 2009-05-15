package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;

public class NEXTTAGRuleBehavior {
	@Test
	public void shouldNotMatch() {
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");

		Token next = mock(Token.class);

		Context context = new Context(new Token[] { token, next });
		
		Rule rule = new NEXTTAGRule("foo", "bar", "foobar");
		assertFalse(rule.matches(context));
	}

	@Test
	public void shouldMatch() {
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");
		
		Token next = mock(Token.class);
		when(next.getTag()).thenReturn("foobar");
		
		Context context = new Context(new Token[] { token, next });
		
		Rule rule = new NEXTTAGRule("foo", "bar", "foobar");
		assertTrue(rule.matches(context));
	}
}