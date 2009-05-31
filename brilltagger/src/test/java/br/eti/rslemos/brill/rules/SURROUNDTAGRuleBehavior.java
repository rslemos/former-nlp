package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class SURROUNDTAGRuleBehavior {
	private boolean matches(String prev1Tag, String next1Tag) {
		Context context = buildContext();
		
		Rule rule = new SURROUNDTAGRule(THIS_TAG, TO_TAG, prev1Tag, next1Tag);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV3_TAG, NEXT1_TAG));
		assertFalse(matches(PREV2_TAG, NEXT1_TAG));
		assertFalse(matches(THIS_TAG,  NEXT1_TAG));
		assertFalse(matches(NEXT1_TAG, NEXT1_TAG));
		assertFalse(matches(NEXT2_TAG, NEXT1_TAG));
		assertFalse(matches(NEXT3_TAG, NEXT1_TAG));
		
		assertFalse(matches(PREV1_TAG, PREV3_TAG));
		assertFalse(matches(PREV1_TAG, PREV2_TAG));
		assertFalse(matches(PREV1_TAG, PREV1_TAG));
		assertFalse(matches(PREV1_TAG, THIS_TAG));
		assertFalse(matches(PREV1_TAG, NEXT2_TAG));
		assertFalse(matches(PREV1_TAG, NEXT3_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV1_TAG, NEXT1_TAG));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(SURROUNDTAGRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(SURROUNDTAGRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleFactoryBehaviorUtils.createAndTestBrillText(SURROUNDTAGRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " SURROUNDTAG " + PREV1_TAG + " " + NEXT1_TAG);
	}
}
