package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTBIGRAMRuleBehavior {
	private boolean matches(String next1Word, String next2Word) {
		Context context = buildContext();
		
		Rule rule = NEXTBIGRAMRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG, next1Word, next2Word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD, NEXT2_WORD));
		assertFalse(matches(PREV3_WORD, NEXT2_WORD));
		assertFalse(matches(PREV2_WORD, NEXT2_WORD));
		assertFalse(matches(PREV1_WORD, NEXT2_WORD));
		assertFalse(matches(THIS_WORD,  NEXT2_WORD));
		assertFalse(matches(NEXT2_WORD, NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD, NEXT2_WORD));
		assertFalse(matches(NEXT4_WORD, NEXT2_WORD));
		
		assertFalse(matches(NEXT1_WORD, PREV4_WORD));
		assertFalse(matches(NEXT1_WORD, PREV3_WORD));
		assertFalse(matches(NEXT1_WORD, PREV2_WORD));
		assertFalse(matches(NEXT1_WORD, PREV1_WORD));
		assertFalse(matches(NEXT1_WORD, THIS_WORD));
		assertFalse(matches(NEXT1_WORD, NEXT1_WORD));
		assertFalse(matches(NEXT1_WORD, NEXT3_WORD));
		assertFalse(matches(NEXT1_WORD, NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT1_WORD, NEXT2_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(NEXTBIGRAMRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(NEXTBIGRAMRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG, NEXT1_WORD, NEXT2_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXTBIGRAMRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXTBIGRAMRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(NEXTBIGRAMRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " NEXTBIGRAM " + NEXT1_WORD + " " + NEXT2_WORD);
	}
}
