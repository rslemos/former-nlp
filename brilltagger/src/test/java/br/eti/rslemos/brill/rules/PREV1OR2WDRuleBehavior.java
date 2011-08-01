package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2WDRuleBehavior {
	private boolean matches(String prev1or2Word) {
		Context context = buildContext();
		
		Rule rule = PREV1OR2WDRule.createRule(THIS_TAG, TO_TAG, prev1or2Word);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD));
		assertFalse(matches(PREV3_WORD));
		assertFalse(matches(THIS_WORD));
		assertFalse(matches(NEXT1_WORD));
		assertFalse(matches(NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD));
		assertFalse(matches(NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_WORD));
		assertTrue(matches(PREV1_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(PREV1OR2WDRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldNotDependOnContextObject() {
		testDependency(PREV1OR2WDRule.createRule(THIS_TAG, THIS_TAG, PREV1_WORD), F, F, F, F, F, F, F, F);
		testDependency(PREV1OR2WDRule.createRule(THIS_TAG, THIS_TAG, PREV2_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREV1OR2WDRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREV1OR2WDRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(PREV1OR2WDRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2WD " + PREV1_WORD,
				THIS_TAG + " " + TO_TAG + " PREV1OR2WD " + PREV2_WORD);
	}
}
