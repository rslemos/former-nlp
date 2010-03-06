package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2TAGRuleBehavior {
	private boolean matches(String next1or2Tag) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new NEXT1OR2TAGRule<String>(THIS_TAG, TO_TAG, next1or2Tag);
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
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(NEXT1OR2TAGRule.<String>FACTORY1());
		createAndTestBasicDependency(NEXT1OR2TAGRule.<String>FACTORY2());
	}
	
	@Test
	public void shouldDependOnContextTag() {
		testDependency(new NEXT1OR2TAGRule<String>(THIS_TAG, THIS_TAG, NEXT1_TAG), F, F, F, F, T, F, F, F);
		testDependency(new NEXT1OR2TAGRule<String>(THIS_TAG, THIS_TAG, NEXT2_TAG), F, F, F, F, F, T, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXT1OR2TAGRule.<String>FACTORY1());
		createAndTestMatchability(NEXT1OR2TAGRule.<String>FACTORY2());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXT1OR2TAGRule.<String>FACTORY1());
		createAndTestObjectSemantics(NEXT1OR2TAGRule.<String>FACTORY2());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(NEXT1OR2TAGRule.<String>FACTORY1(), 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2TAG " + NEXT1_TAG);
		createAndTestBrillText(NEXT1OR2TAGRule.<String>FACTORY2(), 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2TAG " + NEXT2_TAG);
	}
}
