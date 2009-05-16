package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTTAGRuleBehavior {
	private boolean matches(String nextTag) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new NEXTTAGRule("this-tag", "to-tag", nextTag);
		return rule.matches(context);
	}

	@Test
	public void shouldNotMatch() {
		assertFalse(matches("other-tag"));
	}

	@Test
	public void shouldMatch() {
		assertTrue(matches("next1-tag"));
	}
}
