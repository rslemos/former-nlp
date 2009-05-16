package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class RBIGRAMRuleBehavior {
	private boolean matches(String word, String nextWord) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new RBIGRAMRule("this-tag", "to-tag", word, nextWord);
		return rule.matches(context);
	}

	@Test
	public void shouldNotMatchBecauseOfWordMismatch() {
		assertFalse(matches("other-word", "next1-word"));
	}

	@Test
	public void shouldNotMatchBecauseOfRightWordMismatch() {
		assertFalse(matches("this-word", "other-word"));
	}

	@Test
	public void shouldMatch() {
		assertTrue(matches("this-word", "next1-word"));
	}
}
