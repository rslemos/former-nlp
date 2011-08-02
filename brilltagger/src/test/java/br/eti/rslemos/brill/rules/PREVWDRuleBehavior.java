package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVWDRuleBehavior {
	private boolean matches(String prevWord) {
		Context context = buildContext();
		
		Rule rule = PREVWDRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG, prevWord);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD));
		assertFalse(matches(PREV3_WORD));
		assertFalse(matches(PREV2_WORD));
		assertFalse(matches(THIS_WORD));
		assertFalse(matches(NEXT1_WORD));
		assertFalse(matches(NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD));
		assertFalse(matches(NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV1_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(PREVWDRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldNotDependOnContextObject() {
		testDependency(PREVWDRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG, PREV1_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREVWDRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREVWDRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(PREVWDRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " PREVWD " + PREV1_WORD);
	}
}
