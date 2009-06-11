package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.F;
import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.T;
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
	public void shouldDependOnFromTag() {
		RuleBehaviorUtils.createAndTestBasicDependency(PREV1OR2OR3TAGRule.FACTORY1);
		RuleBehaviorUtils.createAndTestBasicDependency(PREV1OR2OR3TAGRule.FACTORY2);
		RuleBehaviorUtils.createAndTestBasicDependency(PREV1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldDependOnContextTag() {
		RuleBehaviorUtils.createAndTestContextDependency(PREV1OR2OR3TAGRule.FACTORY1, F, F, F, T, F, F, F, F);
		RuleBehaviorUtils.createAndTestContextDependency(PREV1OR2OR3TAGRule.FACTORY2, F, F, T, F, F, F, F, F);
		RuleBehaviorUtils.createAndTestContextDependency(PREV1OR2OR3TAGRule.FACTORY3, F, T, F, F, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		RuleBehaviorUtils.createAndTestMatchability(PREV1OR2OR3TAGRule.FACTORY1);
		RuleBehaviorUtils.createAndTestMatchability(PREV1OR2OR3TAGRule.FACTORY2);
		RuleBehaviorUtils.createAndTestMatchability(PREV1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleBehaviorUtils.createAndTestObjectSemantics(PREV1OR2OR3TAGRule.FACTORY1);
		RuleBehaviorUtils.createAndTestObjectSemantics(PREV1OR2OR3TAGRule.FACTORY2);
		RuleBehaviorUtils.createAndTestObjectSemantics(PREV1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleBehaviorUtils.createAndTestBrillText(PREV1OR2OR3TAGRule.FACTORY1, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3TAG " + PREV1_TAG);
		RuleBehaviorUtils.createAndTestBrillText(PREV1OR2OR3TAGRule.FACTORY2, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3TAG " + PREV2_TAG);
		RuleBehaviorUtils.createAndTestBrillText(PREV1OR2OR3TAGRule.FACTORY3, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3TAG " + PREV3_TAG);
	}
}
