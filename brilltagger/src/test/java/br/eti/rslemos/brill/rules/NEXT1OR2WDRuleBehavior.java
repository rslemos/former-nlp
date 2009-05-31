package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2WDRuleBehavior {
	private boolean matches(String next1or2Word) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new NEXT1OR2WDRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, next1or2Word);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(RuleContextMother.PREV3_WORD));
		assertFalse(matches(RuleContextMother.PREV2_WORD));
		assertFalse(matches(RuleContextMother.PREV1_WORD));
		assertFalse(matches(RuleContextMother.THIS_WORD));
		assertFalse(matches(RuleContextMother.NEXT3_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(RuleContextMother.NEXT1_WORD));
		assertTrue(matches(RuleContextMother.NEXT2_WORD));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(NEXT1OR2WDRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTest(NEXT1OR2WDRule.FACTORY2);
	}
}
