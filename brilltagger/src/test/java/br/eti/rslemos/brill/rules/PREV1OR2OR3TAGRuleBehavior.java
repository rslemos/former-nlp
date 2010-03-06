package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3TAGRuleBehavior {
	private boolean matches(String prev1or2or3Tag) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new PREV1OR2OR3TAGRule<String>(THIS_TAG, TO_TAG, prev1or2or3Tag);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT1_TAG));
		assertFalse(matches(NEXT2_TAG));
		assertFalse(matches(NEXT3_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV3_TAG));
		assertTrue(matches(PREV2_TAG));
		assertTrue(matches(PREV1_TAG));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(PREV1OR2OR3TAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldDependOnContextTag() {
		testDependency(new PREV1OR2OR3TAGRule<String>(THIS_TAG, THIS_TAG, PREV1_TAG), F, F, F, T, F, F, F, F);
		testDependency(new PREV1OR2OR3TAGRule<String>(THIS_TAG, THIS_TAG, PREV2_TAG), F, F, T, F, F, F, F, F);
		testDependency(new PREV1OR2OR3TAGRule<String>(THIS_TAG, THIS_TAG, PREV3_TAG), F, T, F, F, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREV1OR2OR3TAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREV1OR2OR3TAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(PREV1OR2OR3TAGRule.<String>FACTORY(), 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3TAG " + PREV1_TAG,
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3TAG " + PREV2_TAG,
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3TAG " + PREV3_TAG);
	}
}
