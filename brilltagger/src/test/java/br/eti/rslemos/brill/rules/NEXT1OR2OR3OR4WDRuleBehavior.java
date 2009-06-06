package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3OR4WDRuleBehavior {
	private boolean matches(String next1or2or3or4Word) {
		Context context = buildContext();
		
		Rule rule = new NEXT1OR2OR3OR4WDRule(THIS_TAG, TO_TAG, next1or2or3or4Word);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD));
		assertFalse(matches(PREV3_WORD));
		assertFalse(matches(PREV2_WORD));
		assertFalse(matches(PREV1_WORD));
		assertFalse(matches(THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT1_WORD));
		assertTrue(matches(NEXT2_WORD));
		assertTrue(matches(NEXT3_WORD));
		assertTrue(matches(NEXT4_WORD));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(NEXT1OR2OR3OR4WDRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTest(NEXT1OR2OR3OR4WDRule.FACTORY2);
		RuleFactoryBehaviorUtils.createAndTest(NEXT1OR2OR3OR4WDRule.FACTORY3);
		RuleFactoryBehaviorUtils.createAndTest(NEXT1OR2OR3OR4WDRule.FACTORY4);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(NEXT1OR2OR3OR4WDRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(NEXT1OR2OR3OR4WDRule.FACTORY2);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(NEXT1OR2OR3OR4WDRule.FACTORY3);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(NEXT1OR2OR3OR4WDRule.FACTORY4);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleFactoryBehaviorUtils.createAndTestBrillText(NEXT1OR2OR3OR4WDRule.FACTORY1, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT1_WORD);
		RuleFactoryBehaviorUtils.createAndTestBrillText(NEXT1OR2OR3OR4WDRule.FACTORY2, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT2_WORD);
		RuleFactoryBehaviorUtils.createAndTestBrillText(NEXT1OR2OR3OR4WDRule.FACTORY3, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT3_WORD);
		RuleFactoryBehaviorUtils.createAndTestBrillText(NEXT1OR2OR3OR4WDRule.FACTORY4, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT4_WORD);
	}
}
