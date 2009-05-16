package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2TAGRuleBehavior {
	private boolean matches(String next1or2Tag) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new NEXT1OR2TAGRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, next1or2Tag);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotMatch() {
		assertFalse(matches(RuleContextMother.NEITHER_TAG));
		// fires on NEXT1OR2OR3TAG
		assertFalse(matches(RuleContextMother.NEXT3_TAG));
	}

	@Test
	public void shouldMatchBecauseOfNext1() {
		assertTrue(matches(RuleContextMother.NEXT1_TAG));
	}

	@Test
	public void shouldMatchBecauseOfNext2() {
		assertTrue(matches(RuleContextMother.NEXT2_TAG));
	}
}
