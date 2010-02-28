package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV2TAGRuleBehavior {
	private boolean matches(String prev2Tag) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new PREV2TAGRule<String>(THIS_TAG, TO_TAG, prev2Tag);
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
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(PREV2TAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldDependOnContextTag() {
		createAndTestContextDependency(PREV2TAGRule.<String>FACTORY(), F, F, T, F, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREV2TAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREV2TAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(PREV2TAGRule.<String>FACTORY(), 
				THIS_TAG + " " + TO_TAG + " PREV2TAG " + PREV2_TAG);
	}
}
