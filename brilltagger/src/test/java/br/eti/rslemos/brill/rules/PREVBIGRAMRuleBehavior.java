package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVBIGRAMRuleBehavior {
	private boolean matches(String prev2Word, String prev1Word) {
		Context context = buildContext();
		
		Rule rule = new PREVBIGRAMRule(THIS_TAG, TO_TAG, prev2Word, prev1Word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD, PREV1_WORD));
		assertFalse(matches(PREV3_WORD, PREV1_WORD));
		assertFalse(matches(PREV1_WORD, PREV1_WORD));
		assertFalse(matches(THIS_WORD,  PREV1_WORD));
		assertFalse(matches(NEXT1_WORD, PREV1_WORD));
		assertFalse(matches(NEXT2_WORD, PREV1_WORD));
		assertFalse(matches(NEXT3_WORD, PREV1_WORD));
		assertFalse(matches(NEXT4_WORD, PREV1_WORD));
		
		assertFalse(matches(PREV2_WORD, PREV4_WORD));
		assertFalse(matches(PREV2_WORD, PREV3_WORD));
		assertFalse(matches(PREV2_WORD, PREV2_WORD));
		assertFalse(matches(PREV2_WORD, THIS_WORD));
		assertFalse(matches(PREV2_WORD, NEXT1_WORD));
		assertFalse(matches(PREV2_WORD, NEXT2_WORD));
		assertFalse(matches(PREV2_WORD, NEXT3_WORD));
		assertFalse(matches(PREV2_WORD, NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_WORD, PREV1_WORD));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(PREVBIGRAMRule.FACTORY);
	}
	
	@Test
	public void shouldNotDependOnContextTag() {
		createAndTestContextIndependency(PREVBIGRAMRule.FACTORY);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREVBIGRAMRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREVBIGRAMRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(PREVBIGRAMRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " PREVBIGRAM " + PREV2_WORD + " " + PREV1_WORD);
	}
}
