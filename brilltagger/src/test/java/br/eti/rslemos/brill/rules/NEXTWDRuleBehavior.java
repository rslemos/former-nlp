package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTWDRuleBehavior {
	private boolean matches(String nextWord) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new NEXTWDRule("this-tag", "to-tag", nextWord);
		return rule.matches(context);
	}

	@Test
	public void shouldNotMatch() {
		assertFalse(matches("other-word"));
	}

	@Test
	public void shouldMatch() {
		assertTrue(matches("next1-word"));
	}
}
