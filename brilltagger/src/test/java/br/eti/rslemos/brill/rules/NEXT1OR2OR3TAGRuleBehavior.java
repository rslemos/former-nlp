package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3TAGRuleBehavior {
	private boolean matches(String next1or2or3Tag) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new NEXT1OR2OR3TAGRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, next1or2or3Tag);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(RuleContextMother.PREV3_TAG));
		assertFalse(matches(RuleContextMother.PREV2_TAG));
		assertFalse(matches(RuleContextMother.PREV1_TAG));
		assertFalse(matches(RuleContextMother.THIS_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(RuleContextMother.NEXT1_TAG));
		assertTrue(matches(RuleContextMother.NEXT2_TAG));
		assertTrue(matches(RuleContextMother.NEXT3_TAG));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(NEXT1OR2OR3TAGRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTest(NEXT1OR2OR3TAGRule.FACTORY2);
		RuleFactoryBehaviorUtils.createAndTest(NEXT1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(NEXT1OR2OR3TAGRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(NEXT1OR2OR3TAGRule.FACTORY2);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(NEXT1OR2OR3TAGRule.FACTORY3);
	}
}
