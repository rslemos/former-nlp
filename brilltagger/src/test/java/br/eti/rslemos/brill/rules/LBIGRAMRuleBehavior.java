package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class LBIGRAMRuleBehavior {
	private boolean matches(String prevWord, String word) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new LBIGRAMRule("this-tag", "to-tag", prevWord, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotMatchBecauseOfWordMismatch() {
		assertFalse(matches("prev1-word", "other-word"));
	}

	@Test
	public void shouldNotMatchBecauseOfLeftWordMismatch() {
		assertFalse(matches("other-word", "this-word"));
	}

	@Test
	public void shouldMatch() {
		assertTrue(matches("prev1-word", "this-word"));
	}
}
