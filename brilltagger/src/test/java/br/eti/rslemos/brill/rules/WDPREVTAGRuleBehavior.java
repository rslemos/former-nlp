package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDPREVTAGRuleBehavior {
	private boolean matches(String prevTag, String word) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new WDPREVTAGRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, prevTag, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotMatchBecauseOfWordMismatch() {
		assertFalse(matches(RuleContextMother.PREV1_TAG, RuleContextMother.OTHER_WORD));
	}

	@Test
	public void shouldNotMatchBecauseOfPrevTagMismatch() {
		assertFalse(matches(RuleContextMother.OTHER_TAG, RuleContextMother.THIS_WORD));
	}

	@Test
	public void shouldMatch() {
		assertTrue(matches(RuleContextMother.PREV1_TAG, RuleContextMother.THIS_WORD));
	}
}
