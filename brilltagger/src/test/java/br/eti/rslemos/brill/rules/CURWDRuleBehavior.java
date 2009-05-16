package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class CURWDRuleBehavior {
	private boolean matches(String word) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new CURWDRule("this-tag", "to-tag", word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotMatch() {
		assertFalse(matches("other-word"));
	}

	@Test
	public void shouldMatch() {
		assertTrue(matches("this-word"));
	}
}
