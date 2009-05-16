package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class LBIGRAMRuleBehavior {
	private boolean matches(String prevWord, String word) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new LBIGRAMRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, prevWord, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotMatchBecauseOfWordMismatch() {
		assertFalse(matches(RuleContextMother.PREV1_WORD, RuleContextMother.OTHER_WORD));
	}

	@Test
	public void shouldNotMatchBecauseOfLeftWordMismatch() {
		assertFalse(matches(RuleContextMother.OTHER_WORD, RuleContextMother.THIS_WORD));
	}

	@Test
	public void shouldMatch() {
		assertTrue(matches(RuleContextMother.PREV1_WORD, RuleContextMother.THIS_WORD));
	}
}
