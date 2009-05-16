package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV2TAGRuleBehavior {
	private boolean matches(String prev2Tag) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new PREV2TAGRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, prev2Tag);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(RuleContextMother.PREV3_TAG));
		assertFalse(matches(RuleContextMother.PREV1_TAG));
		assertFalse(matches(RuleContextMother.THIS_TAG));
		assertFalse(matches(RuleContextMother.NEXT1_TAG));
		assertFalse(matches(RuleContextMother.NEXT2_TAG));
		assertFalse(matches(RuleContextMother.NEXT3_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(RuleContextMother.PREV2_TAG));
	}
}
