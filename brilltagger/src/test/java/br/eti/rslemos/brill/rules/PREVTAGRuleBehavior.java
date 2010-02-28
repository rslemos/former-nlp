package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVTAGRuleBehavior {
	private boolean matches(String prevTag) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new PREVTAGRule<String>(THIS_TAG, TO_TAG, prevTag);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(PREV3_TAG));
		assertFalse(matches(PREV2_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT1_TAG));
		assertFalse(matches(NEXT2_TAG));
		assertFalse(matches(NEXT3_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV1_TAG));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(PREVTAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldDependOnContextTag() {
		createAndTestContextDependency(PREVTAGRule.<String>FACTORY(), F, F, F, T, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREVTAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREVTAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(PREVTAGRule.<String>FACTORY(), 
				THIS_TAG + " " + TO_TAG + " PREVTAG " + PREV1_TAG);
	}
}
