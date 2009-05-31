package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDPREVTAGRuleBehavior {
	private boolean matches(String prevTag, String word) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new WDPREVTAGRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, prevTag, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.PREV3_WORD));
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.PREV2_WORD));
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.PREV1_WORD));
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.NEXT1_WORD));
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.NEXT2_WORD));
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.NEXT3_WORD));
		
		assertFalse(matches(RuleContextMother.PREV3_TAG, RuleContextMother.THIS_WORD));
		assertFalse(matches(RuleContextMother.PREV2_TAG, RuleContextMother.THIS_WORD));
		assertFalse(matches(RuleContextMother.THIS_TAG,  RuleContextMother.THIS_WORD));
		assertFalse(matches(RuleContextMother.NEXT1_TAG, RuleContextMother.THIS_WORD));
		assertFalse(matches(RuleContextMother.NEXT2_TAG, RuleContextMother.THIS_WORD));
		assertFalse(matches(RuleContextMother.NEXT3_TAG, RuleContextMother.THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(RuleContextMother.PREV1_TAG, RuleContextMother.THIS_WORD));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(WDPREVTAGRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(WDPREVTAGRule.FACTORY);
	}
}
