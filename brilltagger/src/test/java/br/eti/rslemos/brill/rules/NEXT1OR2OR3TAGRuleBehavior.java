package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;

public class NEXT1OR2OR3TAGRuleBehavior {
	private boolean matches(String next1or2or3Tag) {
		Context context = buildContext();
		
		Rule rule = new NEXT1OR2OR3TAGRule("foo", "bar", next1or2or3Tag);
		return rule.matches(context);
	}
	
	private Context buildContext() {
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn("foo");

		Token next1 = mock(Token.class);
		when(next1.getTag()).thenReturn("next1-tag");

		Token next2 = mock(Token.class);
		when(next2.getTag()).thenReturn("next2-tag");
		
		Token next3 = mock(Token.class);
		when(next3.getTag()).thenReturn("next3-tag");

		Context context = new Context(new Token[] { token, next1, next2, next3 });
		return context;
	}

	@Test
	public void shouldNotMatch() {
		assertFalse(matches("neither-tag"));
	}

	@Test
	public void shouldMatchBecauseOfNext1() {
		assertTrue(matches("next1-tag"));
	}

	@Test
	public void shouldMatchBecauseOfNext2() {
		assertTrue(matches("next2-tag"));
	}

	@Test
	public void shouldMatchBecauseOfNext3() {
		assertTrue(matches("next3-tag"));
	}

}
