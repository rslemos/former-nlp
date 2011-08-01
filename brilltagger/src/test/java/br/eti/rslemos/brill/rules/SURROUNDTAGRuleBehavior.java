package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class SURROUNDTAGRuleBehavior {
	private boolean matches(Object prev1Object, Object next1Object) {
		Context context = buildContext();
		
		Rule rule = SURROUNDTAGRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG, prev1Object, next1Object);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG, NEXT1_TAG));
		assertFalse(matches(PREV3_TAG, NEXT1_TAG));
		assertFalse(matches(PREV2_TAG, NEXT1_TAG));
		assertFalse(matches(THIS_TAG,  NEXT1_TAG));
		assertFalse(matches(NEXT1_TAG, NEXT1_TAG));
		assertFalse(matches(NEXT2_TAG, NEXT1_TAG));
		assertFalse(matches(NEXT3_TAG, NEXT1_TAG));
		assertFalse(matches(NEXT4_TAG, NEXT1_TAG));
		
		assertFalse(matches(PREV1_TAG, PREV4_TAG));
		assertFalse(matches(PREV1_TAG, PREV3_TAG));
		assertFalse(matches(PREV1_TAG, PREV2_TAG));
		assertFalse(matches(PREV1_TAG, PREV1_TAG));
		assertFalse(matches(PREV1_TAG, THIS_TAG));
		assertFalse(matches(PREV1_TAG, NEXT2_TAG));
		assertFalse(matches(PREV1_TAG, NEXT3_TAG));
		assertFalse(matches(PREV1_TAG, NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV1_TAG, NEXT1_TAG));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(SURROUNDTAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(SURROUNDTAGRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG, PREV1_TAG, NEXT1_TAG), F, F, F, T, T, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(SURROUNDTAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(SURROUNDTAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(SURROUNDTAGRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " SURROUNDTAG " + PREV1_TAG + " " + NEXT1_TAG);
	}
}
