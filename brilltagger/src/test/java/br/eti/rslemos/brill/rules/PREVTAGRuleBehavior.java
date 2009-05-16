package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVTAGRuleBehavior {
	private boolean matches(String prevTag) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new PREVTAGRule("this-tag", "to-tag", prevTag);
		return rule.matches(context);
	}

	@Test
	public void shouldNotMatch() {
		assertFalse(matches("other-tag"));
	}

	@Test
	public void shouldMatch() {
		assertTrue(matches("prev1-tag"));
	}
}
