package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVWDRuleBehavior {
	private boolean matches(String prevWord) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new PREVWDRule<String>(THIS_TAG, TO_TAG, prevWord);
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
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(PREVWDRule.<String>FACTORY());
	}
	
	@Test
	public void shouldNotDependOnContextTag() {
		createAndTestContextIndependency(PREVWDRule.<String>FACTORY());
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREVWDRule.<String>FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREVWDRule.<String>FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(PREVWDRule.<String>FACTORY(), 
				THIS_TAG + " " + TO_TAG + " PREVWD " + PREV1_WORD);
	}
}
