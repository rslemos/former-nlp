package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV2TAGRuleBehavior {
	private boolean matches(Object prev2Object) {
		Context context = buildContext();
		
		Rule rule = PREV2TAGRule.createRule(THIS_TAG, TO_TAG, prev2Object);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(PREV3_TAG));
		assertFalse(matches(PREV1_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT1_TAG));
		assertFalse(matches(NEXT2_TAG));
		assertFalse(matches(NEXT3_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_TAG));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(PREV2TAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(PREV2TAGRule.createRule(THIS_TAG, THIS_TAG, PREV2_TAG), F, F, T, F, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREV2TAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREV2TAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(PREV2TAGRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " PREV2TAG " + PREV2_TAG);
	}
}
