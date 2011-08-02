package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class RBIGRAMRuleBehavior {
	private boolean matches(String word, String nextWord) {
		Context context = buildContext();
		
		Rule rule = RBIGRAMRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG, word, nextWord);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD, NEXT1_WORD));
		assertFalse(matches(PREV3_WORD, NEXT1_WORD));
		assertFalse(matches(PREV2_WORD, NEXT1_WORD));
		assertFalse(matches(PREV1_WORD, NEXT1_WORD));
		assertFalse(matches(NEXT1_WORD, NEXT1_WORD));
		assertFalse(matches(NEXT2_WORD, NEXT1_WORD));
		assertFalse(matches(NEXT3_WORD, NEXT1_WORD));
		assertFalse(matches(NEXT4_WORD, NEXT1_WORD));
		
		assertFalse(matches(THIS_WORD, PREV4_WORD));
		assertFalse(matches(THIS_WORD, PREV3_WORD));
		assertFalse(matches(THIS_WORD, PREV2_WORD));
		assertFalse(matches(THIS_WORD, PREV1_WORD));
		assertFalse(matches(THIS_WORD,  THIS_WORD));
		assertFalse(matches(THIS_WORD, NEXT2_WORD));
		assertFalse(matches(THIS_WORD, NEXT3_WORD));
		assertFalse(matches(THIS_WORD, NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(THIS_WORD, NEXT1_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(RBIGRAMRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldNotDependOnContextObject() {
		testDependency(RBIGRAMRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG, THIS_WORD, NEXT1_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(RBIGRAMRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(RBIGRAMRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(RBIGRAMRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " RBIGRAM " + THIS_WORD + " " + NEXT1_WORD);
	}
}
