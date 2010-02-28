package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV2WDRuleBehavior {
	private boolean matches(String prev2Word) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new PREV2WDRule<String>(THIS_TAG, TO_TAG, prev2Word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD));
		assertFalse(matches(PREV3_WORD));
		assertFalse(matches(PREV1_WORD));
		assertFalse(matches(THIS_WORD));
		assertFalse(matches(NEXT1_WORD));
		assertFalse(matches(NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD));
		assertFalse(matches(NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_WORD));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(PREV2WDRule.<String>FACTORY());
	}
	
	@Test
	public void shouldNotDependOnContextTag() {
		createAndTestContextIndependency(PREV2WDRule.<String>FACTORY());
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREV2WDRule.<String>FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREV2WDRule.<String>FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(PREV2WDRule.<String>FACTORY(), 
				THIS_TAG + " " + TO_TAG + " PREV2WD " + PREV2_WORD);
	}
}
