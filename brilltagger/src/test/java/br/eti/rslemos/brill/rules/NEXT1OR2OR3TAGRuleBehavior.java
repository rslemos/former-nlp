package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3TAGRuleBehavior {
	private boolean matches(String next1or2or3Tag) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new NEXT1OR2OR3TAGRule("this-tag", "to-tag", next1or2or3Tag);
		return rule.matches(context);
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
