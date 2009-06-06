package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3OR4WDRuleBehavior {
	private boolean matches(String prev1or2or3Word) {
		Context context = buildContext();
		
		Rule rule = new PREV1OR2OR3OR4WDRule(THIS_TAG, TO_TAG, prev1or2or3Word);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(THIS_WORD));
		assertFalse(matches(NEXT1_WORD));
		assertFalse(matches(NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD));
		assertFalse(matches(NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV4_WORD));
		assertTrue(matches(PREV3_WORD));
		assertTrue(matches(PREV2_WORD));
		assertTrue(matches(PREV1_WORD));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(PREV1OR2OR3OR4WDRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTest(PREV1OR2OR3OR4WDRule.FACTORY2);
		RuleFactoryBehaviorUtils.createAndTest(PREV1OR2OR3OR4WDRule.FACTORY3);
		RuleFactoryBehaviorUtils.createAndTest(PREV1OR2OR3OR4WDRule.FACTORY4);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(PREV1OR2OR3OR4WDRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(PREV1OR2OR3OR4WDRule.FACTORY2);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(PREV1OR2OR3OR4WDRule.FACTORY3);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(PREV1OR2OR3OR4WDRule.FACTORY4);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleFactoryBehaviorUtils.createAndTestBrillText(PREV1OR2OR3OR4WDRule.FACTORY1, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3OR4WD " + PREV1_WORD);
		RuleFactoryBehaviorUtils.createAndTestBrillText(PREV1OR2OR3OR4WDRule.FACTORY2, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3OR4WD " + PREV2_WORD);
		RuleFactoryBehaviorUtils.createAndTestBrillText(PREV1OR2OR3OR4WDRule.FACTORY3, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3OR4WD " + PREV3_WORD);
		RuleFactoryBehaviorUtils.createAndTestBrillText(PREV1OR2OR3OR4WDRule.FACTORY4, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3OR4WD " + PREV4_WORD);
	}
}
