package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVTAGRuleBehavior {
	private boolean matches(String prevTag) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new PREVTAGRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, prevTag);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(RuleContextMother.OTHER_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(RuleContextMother.PREV1_TAG));
	}
}
