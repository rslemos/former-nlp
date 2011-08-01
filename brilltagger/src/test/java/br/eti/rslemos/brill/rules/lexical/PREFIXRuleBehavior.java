package br.eti.rslemos.brill.rules.lexical;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.lexical.PREFIXRule;

public class PREFIXRuleBehavior {
	private boolean matches(String prefix) {
		Context context = buildUntaggedContext();
		
		Rule rule = PREFIXRule.createRule(null, TO_TAG, prefix);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV1_WORD.substring(0, 4)));
		assertFalse(matches(NEXT1_WORD.substring(0, 4)));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(THIS_WORD.substring(0, 4)));
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestUntaggedMatchability(PREFIXRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREFIXRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(PREFIXRuleFactory.INSTANCE, 
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 1),
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 2),
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 3),
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 4),
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 5),
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 6),
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 7),
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 8));
	}
}
