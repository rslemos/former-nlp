package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;

public class PREVWDRuleBehavior {
	@Test
	public void shouldNotMatch() {
		Token prev = mock(Token.class);
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");
		
		Context context = new Context(new Token[] { prev, token });
		context.advance();
		
		Rule rule = new PREVWDRule("foo", "bar", "prev-word");
		assertFalse(rule.matches(context));
	}

	@Test
	public void shouldMatch() {
		Token prev = mock(Token.class);
		when(prev.getWord()).thenReturn("prev-word");
		
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");
		
		Context context = new Context(new Token[] { prev, token });
		context.advance();
		
		Rule rule = new PREVWDRule("foo", "bar", "prev-word");
		assertTrue(rule.matches(context));
	}
}
