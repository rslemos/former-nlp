package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class CURWDRuleBehavior {
	private boolean matches(String word) {
		Context context = buildContext();
		
		Rule rule = new CURWDRule(THIS_TAG, TO_TAG, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD));
		assertFalse(matches(PREV3_WORD));
		assertFalse(matches(PREV2_WORD));
		assertFalse(matches(PREV1_WORD));
		assertFalse(matches(NEXT1_WORD));
		assertFalse(matches(NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD));
		assertFalse(matches(NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(THIS_WORD));
	}
	
	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(CURWDRule.FACTORY);
	}

	@Test
	public void shouldDependOnContextObject() {
		testDependency(new CURWDRule(THIS_TAG, THIS_TAG, THIS_WORD), F, F, F, F, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(CURWDRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(CURWDRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(CURWDRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " CURWD " + THIS_WORD);
	}
}
