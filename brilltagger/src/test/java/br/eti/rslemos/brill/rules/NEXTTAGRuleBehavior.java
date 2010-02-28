package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTTAGRuleBehavior {
	private boolean matches(String nextTag) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new NEXTTAGRule<String>(THIS_TAG, TO_TAG, nextTag);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(PREV3_TAG));
		assertFalse(matches(PREV2_TAG));
		assertFalse(matches(PREV1_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT2_TAG));
		assertFalse(matches(NEXT3_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT1_TAG));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(NEXTTAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldDependOnContextTag() {
		createAndTestContextDependency(NEXTTAGRule.<String>FACTORY(), F, F, F, F, T, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXTTAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXTTAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(NEXTTAGRule.<String>FACTORY(), 
				THIS_TAG + " " + TO_TAG + " NEXTTAG " + NEXT1_TAG);
	}
}
