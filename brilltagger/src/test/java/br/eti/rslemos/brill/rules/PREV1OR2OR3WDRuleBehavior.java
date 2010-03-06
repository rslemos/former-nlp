package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3WDRuleBehavior {
	private boolean matches(String prev1or2or3Word) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new PREV1OR2OR3WDRule<String>(THIS_TAG, TO_TAG, prev1or2or3Word);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD));
		assertFalse(matches(THIS_WORD));
		assertFalse(matches(NEXT1_WORD));
		assertFalse(matches(NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD));
		assertFalse(matches(NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV3_WORD));
		assertTrue(matches(PREV2_WORD));
		assertTrue(matches(PREV1_WORD));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(PREV1OR2OR3WDRule.<String>FACTORY1());
		createAndTestBasicDependency(PREV1OR2OR3WDRule.<String>FACTORY2());
		createAndTestBasicDependency(PREV1OR2OR3WDRule.<String>FACTORY3());
	}
	
	@Test
	public void shouldNotDependOnContextTag() {
		testDependency(new PREV1OR2OR3WDRule<String>(THIS_TAG, THIS_TAG, PREV1_WORD), F, F, F, F, F, F, F, F);
		testDependency(new PREV1OR2OR3WDRule<String>(THIS_TAG, THIS_TAG, PREV2_WORD), F, F, F, F, F, F, F, F);
		testDependency(new PREV1OR2OR3WDRule<String>(THIS_TAG, THIS_TAG, PREV3_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREV1OR2OR3WDRule.<String>FACTORY1());
		createAndTestMatchability(PREV1OR2OR3WDRule.<String>FACTORY2());
		createAndTestMatchability(PREV1OR2OR3WDRule.<String>FACTORY3());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREV1OR2OR3WDRule.<String>FACTORY1());
		createAndTestObjectSemantics(PREV1OR2OR3WDRule.<String>FACTORY2());
		createAndTestObjectSemantics(PREV1OR2OR3WDRule.<String>FACTORY3());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(PREV1OR2OR3WDRule.<String>FACTORY1(), 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3WD " + PREV1_WORD);
		createAndTestBrillText(PREV1OR2OR3WDRule.<String>FACTORY2(), 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3WD " + PREV2_WORD);
		createAndTestBrillText(PREV1OR2OR3WDRule.<String>FACTORY3(), 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3WD " + PREV3_WORD);
	}
}
