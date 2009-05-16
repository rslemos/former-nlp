package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVWDRuleBehavior {
	private boolean matches(String prevWord) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new PREVWDRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, prevWord);
		return rule.matches(context);
	}

	@Test
	public void shouldNotMatch() {
		assertFalse(matches(RuleContextMother.OTHER_WORD));
	}

	@Test
	public void shouldMatch() {
		assertTrue(matches(RuleContextMother.PREV1_WORD));
	}
}
