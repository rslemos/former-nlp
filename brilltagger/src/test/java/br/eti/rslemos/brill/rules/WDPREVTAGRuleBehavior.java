package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDPREVTAGRuleBehavior {
	private boolean matches(String prevTag, String word) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new WDPREVTAGRule("this-tag", "to-tag", prevTag, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotMatchBecauseOfWordMismatch() {
		assertFalse(matches("prev1-tag", "other-word"));
	}

	@Test
	public void shouldNotMatchBecauseOfPrevTagMismatch() {
		assertFalse(matches("other-tag", "this-word"));
	}

	@Test
	public void shouldMatch() {
		assertTrue(matches("prev1-tag", "this-word"));
	}
}
