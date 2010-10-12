package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3OR4WDRuleBehavior {
	private boolean matches(String prev1or2or3Word) {
		Context context = buildContext();
		
		Rule rule = new PREV1OR2OR3OR4WDRule(THIS_TAG, TO_TAG, prev1or2or3Word);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(THIS_WORD));
		assertFalse(matches(NEXT1_WORD));
		assertFalse(matches(NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD));
		assertFalse(matches(NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV4_WORD));
		assertTrue(matches(PREV3_WORD));
		assertTrue(matches(PREV2_WORD));
		assertTrue(matches(PREV1_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(PREV1OR2OR3OR4WDRule.FACTORY);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(new PREV1OR2OR3OR4WDRule(THIS_TAG, THIS_TAG, PREV1_WORD), F, F, F, F, F, F, F, F);
		testDependency(new PREV1OR2OR3OR4WDRule(THIS_TAG, THIS_TAG, PREV2_WORD), F, F, F, F, F, F, F, F);
		testDependency(new PREV1OR2OR3OR4WDRule(THIS_TAG, THIS_TAG, PREV3_WORD), F, F, F, F, F, F, F, F);
		testDependency(new PREV1OR2OR3OR4WDRule(THIS_TAG, THIS_TAG, PREV4_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREV1OR2OR3OR4WDRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREV1OR2OR3OR4WDRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(PREV1OR2OR3OR4WDRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3OR4WD " + PREV1_WORD,
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3OR4WD " + PREV2_WORD,
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3OR4WD " + PREV3_WORD,
				THIS_TAG + " " + TO_TAG + " PREV1OR2OR3OR4WD " + PREV4_WORD);
	}
}
