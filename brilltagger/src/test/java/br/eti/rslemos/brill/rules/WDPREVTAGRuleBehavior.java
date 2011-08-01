package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDPREVTAGRuleBehavior {
	private boolean matches(Object prevObject, String word) {
		Context context = buildContext();
		
		Rule rule = WDPREVTAGRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG, prevObject, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV1_TAG, PREV4_WORD));
		assertFalse(matches(PREV1_TAG, PREV3_WORD));
		assertFalse(matches(PREV1_TAG, PREV2_WORD));
		assertFalse(matches(PREV1_TAG, PREV1_WORD));
		assertFalse(matches(PREV1_TAG, NEXT1_WORD));
		assertFalse(matches(PREV1_TAG, NEXT2_WORD));
		assertFalse(matches(PREV1_TAG, NEXT3_WORD));
		assertFalse(matches(PREV1_TAG, NEXT4_WORD));
		
		assertFalse(matches(PREV4_TAG, THIS_WORD));
		assertFalse(matches(PREV3_TAG, THIS_WORD));
		assertFalse(matches(PREV2_TAG, THIS_WORD));
		assertFalse(matches(THIS_TAG,  THIS_WORD));
		assertFalse(matches(NEXT1_TAG, THIS_WORD));
		assertFalse(matches(NEXT2_TAG, THIS_WORD));
		assertFalse(matches(NEXT3_TAG, THIS_WORD));
		assertFalse(matches(NEXT4_TAG, THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV1_TAG, THIS_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(WDPREVTAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(WDPREVTAGRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG, PREV1_TAG, THIS_WORD), F, F, F, T, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(WDPREVTAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(WDPREVTAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(WDPREVTAGRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " WDPREVTAG " + PREV1_TAG + " " + THIS_WORD);
	}
}
