package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class LBIGRAMRuleBehavior {
	private boolean matches(String prevWord, String word) {
		Context context = buildContext();
		
		Rule rule = new LBIGRAMRule(THIS_TAG, TO_TAG, prevWord, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV1_WORD, PREV4_WORD));
		assertFalse(matches(PREV1_WORD, PREV3_WORD));
		assertFalse(matches(PREV1_WORD, PREV2_WORD));
		assertFalse(matches(PREV1_WORD, PREV1_WORD));
		assertFalse(matches(PREV1_WORD, NEXT1_WORD));
		assertFalse(matches(PREV1_WORD, NEXT2_WORD));
		assertFalse(matches(PREV1_WORD, NEXT3_WORD));
		assertFalse(matches(PREV1_WORD, NEXT4_WORD));
		
		assertFalse(matches(PREV4_WORD, THIS_WORD));
		assertFalse(matches(PREV3_WORD, THIS_WORD));
		assertFalse(matches(PREV2_WORD, THIS_WORD));
		assertFalse(matches(THIS_WORD,  THIS_WORD));
		assertFalse(matches(NEXT1_WORD, THIS_WORD));
		assertFalse(matches(NEXT2_WORD, THIS_WORD));
		assertFalse(matches(NEXT3_WORD, THIS_WORD));
		assertFalse(matches(NEXT4_WORD, THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV1_WORD, THIS_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(LBIGRAMRule.FACTORY);
	}

	@Test
	public void shouldDependOnContextObject() {
		testDependency(new LBIGRAMRule(THIS_TAG, THIS_TAG, PREV1_WORD, THIS_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(LBIGRAMRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(LBIGRAMRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(LBIGRAMRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " LBIGRAM " + PREV1_WORD + " " + THIS_WORD);
	}
}
