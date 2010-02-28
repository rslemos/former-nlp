package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTBIGRAMRuleBehavior {
	private boolean matches(String next1Word, String next2Word) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new NEXTBIGRAMRule<String>(THIS_TAG, TO_TAG, next1Word, next2Word);
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
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(NEXTBIGRAMRule.<String>FACTORY());
	}
	
	@Test
	public void shouldDependOnContextTag() {
		createAndTestContextIndependency(NEXTBIGRAMRule.<String>FACTORY());
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXTBIGRAMRule.<String>FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXTBIGRAMRule.<String>FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(NEXTBIGRAMRule.<String>FACTORY(), 
				THIS_TAG + " " + TO_TAG + " NEXTBIGRAM " + NEXT1_WORD + " " + NEXT2_WORD);
	}
}
