package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT2TAGRuleBehavior {
	private boolean matches(Object next2Object) {
		Context context = buildContext();
		
		Rule rule = NEXT2TAGRule.createRule(THIS_TAG, TO_TAG, next2Object);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(PREV3_TAG));
		assertFalse(matches(PREV2_TAG));
		assertFalse(matches(PREV1_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT1_TAG));
		assertFalse(matches(NEXT3_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT2_TAG));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(NEXT2TAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(NEXT2TAGRule.createRule(THIS_TAG, THIS_TAG, NEXT2_TAG), F, F, F, F, F, T, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXT2TAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXT2TAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(NEXT2TAGRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " NEXT2TAG " + NEXT2_TAG);
	}
}
