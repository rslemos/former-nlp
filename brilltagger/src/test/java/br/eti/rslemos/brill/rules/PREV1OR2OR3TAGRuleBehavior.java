package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3TAGRuleBehavior {
	private boolean matches(String prev1or2or3Tag) {
		Context context = buildContext();
		
		Rule rule = new PREV1OR2OR3TAGRule(THIS_TAG, TO_TAG, prev1or2or3Tag);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT1_TAG));
		assertFalse(matches(NEXT2_TAG));
		assertFalse(matches(NEXT3_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV3_TAG));
		assertTrue(matches(PREV2_TAG));
		assertTrue(matches(PREV1_TAG));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(PREV1OR2OR3TAGRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTest(PREV1OR2OR3TAGRule.FACTORY2);
		RuleFactoryBehaviorUtils.createAndTest(PREV1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(PREV1OR2OR3TAGRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(PREV1OR2OR3TAGRule.FACTORY2);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(PREV1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleFactoryBehaviorUtils.createAndTestBrillText(PREV1OR2OR3TAGRule.FACTORY1, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3TAG " + PREV1_TAG);
		RuleFactoryBehaviorUtils.createAndTestBrillText(PREV1OR2OR3TAGRule.FACTORY2, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3TAG " + PREV2_TAG);
		RuleFactoryBehaviorUtils.createAndTestBrillText(PREV1OR2OR3TAGRule.FACTORY3, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3TAG " + PREV3_TAG);
	}
}
