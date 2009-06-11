package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGBFRRuleBehavior {
	private boolean matches(String prev2Tag, String word) {
		Context context = buildContext();
		
		Rule rule = new WDAND2TAGBFRRule(THIS_TAG, TO_TAG, prev2Tag, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV2_TAG, PREV4_WORD));
		assertFalse(matches(PREV2_TAG, PREV3_WORD));
		assertFalse(matches(PREV2_TAG, PREV2_WORD));
		assertFalse(matches(PREV2_TAG, PREV1_WORD));
		assertFalse(matches(PREV2_TAG, NEXT1_WORD));
		assertFalse(matches(PREV2_TAG, NEXT2_WORD));
		assertFalse(matches(PREV2_TAG, NEXT3_WORD));
		assertFalse(matches(PREV2_TAG, NEXT4_WORD));
		
		assertFalse(matches(PREV4_TAG, THIS_WORD));
		assertFalse(matches(PREV3_TAG, THIS_WORD));
		assertFalse(matches(PREV1_TAG, THIS_WORD));
		assertFalse(matches(THIS_TAG,  THIS_WORD));
		assertFalse(matches(NEXT1_TAG, THIS_WORD));
		assertFalse(matches(NEXT2_TAG, THIS_WORD));
		assertFalse(matches(NEXT3_TAG, THIS_WORD));
		assertFalse(matches(NEXT4_TAG, THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_TAG, THIS_WORD));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(WDAND2TAGBFRRule.FACTORY);
	}
	
	@Test
	public void shouldDependOnContextTag() {
		createAndTestContextDependency(WDAND2TAGBFRRule.FACTORY, F, F, T, F, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(WDAND2TAGBFRRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(WDAND2TAGBFRRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(WDAND2TAGBFRRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " WDAND2TAGBFR " + PREV2_TAG + " " + THIS_WORD);
	}
}
