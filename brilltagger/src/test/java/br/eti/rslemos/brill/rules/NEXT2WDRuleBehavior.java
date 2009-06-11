package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT2WDRuleBehavior {
	private boolean matches(String nextWord) {
		Context context = buildContext();
		
		Rule rule = new NEXT2WDRule(THIS_TAG, TO_TAG, nextWord);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD));
		assertFalse(matches(PREV3_WORD));
		assertFalse(matches(PREV2_WORD));
		assertFalse(matches(PREV1_WORD));
		assertFalse(matches(THIS_WORD));
		assertFalse(matches(NEXT1_WORD));
		assertFalse(matches(NEXT3_WORD));
		assertFalse(matches(NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT2_WORD));
	}

	@Test
	public void shouldDependOnFromTag() {
		RuleBehaviorUtils.createAndTestBasicDependency(NEXT2WDRule.FACTORY);
	}
	
	@Test
	public void shouldDependOnContextTag() {
		RuleBehaviorUtils.createAndTestContextIndependency(NEXT2WDRule.FACTORY);
	}

	@Test
	public void shouldCreateRule() {
		RuleBehaviorUtils.createAndTestMatchability(NEXT2WDRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleBehaviorUtils.createAndTestObjectSemantics(NEXT2WDRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleBehaviorUtils.createAndTestBrillText(NEXT2WDRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " NEXT2WD " + NEXT2_WORD);
	}
}
