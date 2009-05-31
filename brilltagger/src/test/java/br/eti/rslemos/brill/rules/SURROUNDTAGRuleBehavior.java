package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class SURROUNDTAGRuleBehavior {
	private boolean matches(String prev1Tag, String next1Tag) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new SURROUNDTAGRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, prev1Tag, next1Tag);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(RuleContextMother.PREV3_TAG, RuleContextMother.NEXT1_TAG));
		assertFalse(matches(RuleContextMother.PREV2_TAG, RuleContextMother.NEXT1_TAG));
		assertFalse(matches(RuleContextMother.THIS_TAG,  RuleContextMother.NEXT1_TAG));
		assertFalse(matches(RuleContextMother.NEXT1_TAG, RuleContextMother.NEXT1_TAG));
		assertFalse(matches(RuleContextMother.NEXT2_TAG, RuleContextMother.NEXT1_TAG));
		assertFalse(matches(RuleContextMother.NEXT3_TAG, RuleContextMother.NEXT1_TAG));
		
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.PREV3_TAG));
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.PREV2_TAG));
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.PREV1_TAG));
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.THIS_TAG));
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.NEXT2_TAG));
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.NEXT3_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(RuleContextMother.PREV1_TAG, RuleContextMother.NEXT1_TAG));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(SURROUNDTAGRule.FACTORY);
	}
}
