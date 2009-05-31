package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2BFRRuleBehavior {
	private boolean matches(String prev2Word, String word) {
		Context context = buildContext();
		
		Rule rule = new WDAND2BFRRule(THIS_TAG, TO_TAG, prev2Word, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV2_WORD, PREV3_WORD));
		assertFalse(matches(PREV2_WORD, PREV2_WORD));
		assertFalse(matches(PREV2_WORD, PREV1_WORD));
		assertFalse(matches(PREV2_WORD, NEXT1_WORD));
		assertFalse(matches(PREV2_WORD, NEXT2_WORD));
		assertFalse(matches(PREV2_WORD, NEXT3_WORD));
		
		assertFalse(matches(PREV3_WORD, THIS_WORD));
		assertFalse(matches(PREV1_WORD, THIS_WORD));
		assertFalse(matches(THIS_WORD,  THIS_WORD));
		assertFalse(matches(NEXT1_WORD, THIS_WORD));
		assertFalse(matches(NEXT2_WORD, THIS_WORD));
		assertFalse(matches(NEXT3_WORD, THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_WORD, THIS_WORD));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(WDAND2BFRRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(WDAND2BFRRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleFactoryBehaviorUtils.createAndTestBrillText(WDAND2BFRRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " WDAND2BFR " + PREV2_WORD + " " + THIS_WORD);
	}
}
