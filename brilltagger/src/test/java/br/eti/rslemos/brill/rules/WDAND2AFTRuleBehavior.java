package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2AFTRuleBehavior {
	private boolean matches(String word, String next2Word) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new WDAND2AFTRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, word, next2Word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(RuleContextMother.PREV3_WORD, RuleContextMother.NEXT2_WORD));
		assertFalse(matches(RuleContextMother.PREV2_WORD, RuleContextMother.NEXT2_WORD));
		assertFalse(matches(RuleContextMother.PREV1_WORD, RuleContextMother.NEXT2_WORD));
		assertFalse(matches(RuleContextMother.NEXT1_WORD, RuleContextMother.NEXT2_WORD));
		assertFalse(matches(RuleContextMother.NEXT2_WORD, RuleContextMother.NEXT2_WORD));
		assertFalse(matches(RuleContextMother.NEXT3_WORD, RuleContextMother.NEXT2_WORD));
		
		assertFalse(matches(RuleContextMother.THIS_WORD, RuleContextMother.PREV3_WORD));
		assertFalse(matches(RuleContextMother.THIS_WORD, RuleContextMother.PREV2_WORD));
		assertFalse(matches(RuleContextMother.THIS_WORD, RuleContextMother.PREV1_WORD));
		assertFalse(matches(RuleContextMother.THIS_WORD,  RuleContextMother.THIS_WORD));
		assertFalse(matches(RuleContextMother.THIS_WORD, RuleContextMother.NEXT1_WORD));
		assertFalse(matches(RuleContextMother.THIS_WORD, RuleContextMother.NEXT3_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(RuleContextMother.THIS_WORD, RuleContextMother.NEXT2_WORD));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(WDAND2AFTRule.FACTORY);
	}
}
