package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2BFRRuleBehavior {
	private boolean matches(String prev2Word, String word) {
		Context context = buildContext();
		
		Rule rule = WDAND2BFRRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG, prev2Word, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV2_WORD, PREV4_WORD));
		assertFalse(matches(PREV2_WORD, PREV3_WORD));
		assertFalse(matches(PREV2_WORD, PREV2_WORD));
		assertFalse(matches(PREV2_WORD, PREV1_WORD));
		assertFalse(matches(PREV2_WORD, NEXT1_WORD));
		assertFalse(matches(PREV2_WORD, NEXT2_WORD));
		assertFalse(matches(PREV2_WORD, NEXT3_WORD));
		assertFalse(matches(PREV2_WORD, NEXT4_WORD));
		
		assertFalse(matches(PREV4_WORD, THIS_WORD));
		assertFalse(matches(PREV3_WORD, THIS_WORD));
		assertFalse(matches(PREV1_WORD, THIS_WORD));
		assertFalse(matches(THIS_WORD,  THIS_WORD));
		assertFalse(matches(NEXT1_WORD, THIS_WORD));
		assertFalse(matches(NEXT2_WORD, THIS_WORD));
		assertFalse(matches(NEXT3_WORD, THIS_WORD));
		assertFalse(matches(NEXT4_WORD, THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_WORD, THIS_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(WDAND2BFRRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldNotDependOnContextObject() {
		testDependency(WDAND2BFRRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG, PREV2_WORD, THIS_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(WDAND2BFRRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(WDAND2BFRRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(WDAND2BFRRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " WDAND2BFR " + PREV2_WORD + " " + THIS_WORD);
	}
}
