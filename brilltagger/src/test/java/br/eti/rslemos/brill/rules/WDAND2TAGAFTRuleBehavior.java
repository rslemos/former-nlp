package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGAFTRuleBehavior {
	private boolean matches(String word, Object next2Object) {
		Context context = buildContext();
		
		Rule rule = WDAND2TAGAFTRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG, word, next2Object);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD, NEXT2_TAG));
		assertFalse(matches(PREV3_WORD, NEXT2_TAG));
		assertFalse(matches(PREV2_WORD, NEXT2_TAG));
		assertFalse(matches(PREV1_WORD, NEXT2_TAG));
		assertFalse(matches(NEXT1_WORD, NEXT2_TAG));
		assertFalse(matches(NEXT2_WORD, NEXT2_TAG));
		assertFalse(matches(NEXT3_WORD, NEXT2_TAG));
		assertFalse(matches(NEXT4_WORD, NEXT2_TAG));
		
		assertFalse(matches(THIS_WORD, PREV4_TAG));
		assertFalse(matches(THIS_WORD, PREV3_TAG));
		assertFalse(matches(THIS_WORD, PREV2_TAG));
		assertFalse(matches(THIS_WORD, PREV1_TAG));
		assertFalse(matches(THIS_WORD,  THIS_TAG));
		assertFalse(matches(THIS_WORD, NEXT1_TAG));
		assertFalse(matches(THIS_WORD, NEXT3_TAG));
		assertFalse(matches(THIS_WORD, NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(THIS_WORD, NEXT2_TAG));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(WDAND2TAGAFTRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(WDAND2TAGAFTRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG, THIS_WORD, NEXT2_TAG), F, F, F, F, F, T, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(WDAND2TAGAFTRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(WDAND2TAGAFTRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(WDAND2TAGAFTRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " WDAND2TAGAFT " + THIS_WORD + " " + NEXT2_TAG);
	}
}
