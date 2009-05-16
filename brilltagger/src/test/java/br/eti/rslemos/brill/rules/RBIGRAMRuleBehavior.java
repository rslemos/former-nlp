package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class RBIGRAMRuleBehavior {
	private boolean matches(String word, String nextWord) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new RBIGRAMRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, word, nextWord);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(RuleContextMother.OTHER_WORD, RuleContextMother.NEXT1_WORD));
		assertFalse(matches(RuleContextMother.THIS_WORD, RuleContextMother.OTHER_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(RuleContextMother.THIS_WORD, RuleContextMother.NEXT1_WORD));
	}
}
