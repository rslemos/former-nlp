package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2AFTRuleBehavior {
	private boolean matches(String word, String next2Word) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new WDAND2AFTRule<String>(THIS_TAG, TO_TAG, word, next2Word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD, NEXT2_WORD));
		assertFalse(matches(PREV3_WORD, NEXT2_WORD));
		assertFalse(matches(PREV2_WORD, NEXT2_WORD));
		assertFalse(matches(PREV1_WORD, NEXT2_WORD));
		assertFalse(matches(NEXT1_WORD, NEXT2_WORD));
		assertFalse(matches(NEXT2_WORD, NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD, NEXT2_WORD));
		assertFalse(matches(NEXT4_WORD, NEXT2_WORD));
		
		assertFalse(matches(THIS_WORD, PREV4_WORD));
		assertFalse(matches(THIS_WORD, PREV3_WORD));
		assertFalse(matches(THIS_WORD, PREV2_WORD));
		assertFalse(matches(THIS_WORD, PREV1_WORD));
		assertFalse(matches(THIS_WORD,  THIS_WORD));
		assertFalse(matches(THIS_WORD, NEXT1_WORD));
		assertFalse(matches(THIS_WORD, NEXT3_WORD));
		assertFalse(matches(THIS_WORD, NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(THIS_WORD, NEXT2_WORD));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(WDAND2AFTRule.<String>FACTORY());
	}
	
	@Test
	public void shouldNotDependOnContextTag() {
		testDependency(new WDAND2AFTRule<String>(THIS_TAG, THIS_TAG, THIS_WORD, NEXT2_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(WDAND2AFTRule.<String>FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(WDAND2AFTRule.<String>FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(WDAND2AFTRule.<String>FACTORY(), 
				THIS_TAG + " " + TO_TAG + " WDAND2AFT " + THIS_WORD + " " + NEXT2_WORD);
	}
}
