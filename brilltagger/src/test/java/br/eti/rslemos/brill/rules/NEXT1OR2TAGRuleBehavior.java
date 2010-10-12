package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2TAGRuleBehavior {
	private boolean matches(Object next1or2Object) {
		Context context = buildContext();
		
		Rule rule = new NEXT1OR2TAGRule(THIS_TAG, TO_TAG, next1or2Object);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(PREV3_TAG));
		assertFalse(matches(PREV2_TAG));
		assertFalse(matches(PREV1_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT3_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT1_TAG));
		assertTrue(matches(NEXT2_TAG));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(NEXT1OR2TAGRule.FACTORY);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(new NEXT1OR2TAGRule(THIS_TAG, THIS_TAG, NEXT1_TAG), F, F, F, F, T, F, F, F);
		testDependency(new NEXT1OR2TAGRule(THIS_TAG, THIS_TAG, NEXT2_TAG), F, F, F, F, F, T, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXT1OR2TAGRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXT1OR2TAGRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(NEXT1OR2TAGRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2TAG " + NEXT1_TAG,
				THIS_TAG + " " + TO_TAG + " NEXT1OR2TAG " + NEXT2_TAG);
	}
}
