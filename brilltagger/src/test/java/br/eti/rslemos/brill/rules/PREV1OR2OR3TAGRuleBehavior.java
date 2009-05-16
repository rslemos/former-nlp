package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3TAGRuleBehavior {
	private boolean matches(String prev1or2or3Tag) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new PREV1OR2OR3TAGRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, prev1or2or3Tag);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(RuleContextMother.THIS_TAG));
		assertFalse(matches(RuleContextMother.NEXT1_TAG));
		assertFalse(matches(RuleContextMother.NEXT2_TAG));
		assertFalse(matches(RuleContextMother.NEXT3_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(RuleContextMother.PREV3_TAG));
		assertTrue(matches(RuleContextMother.PREV2_TAG));
		assertTrue(matches(RuleContextMother.PREV1_TAG));
	}
}
