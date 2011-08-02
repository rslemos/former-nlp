package br.eti.rslemos.brill.rules.lexical;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.createAndTestBrillString;
import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.createAndTestObjectSemantics;
import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.createAndTestUntaggedMatchability;
import static br.eti.rslemos.brill.rules.RuleContextMother.NEXT1_WORD;
import static br.eti.rslemos.brill.rules.RuleContextMother.PREV1_WORD;
import static br.eti.rslemos.brill.rules.RuleContextMother.THIS_WORD;
import static br.eti.rslemos.brill.rules.RuleContextMother.TO_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.buildUntaggedContext;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREFIXRuleBehavior {
	private boolean matches(String prefix) {
		Context context = buildUntaggedContext();
		
		Rule rule = PREFIXRuleFactory.INSTANCE.createRule(null, TO_TAG, prefix);
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
