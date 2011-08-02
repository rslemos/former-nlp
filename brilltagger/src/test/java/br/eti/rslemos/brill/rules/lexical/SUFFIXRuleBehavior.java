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

public class SUFFIXRuleBehavior {
	private boolean matches(String suffix) {
		Context context = buildUntaggedContext();
		
		Rule rule = SUFFIXRuleFactory.INSTANCE.createRule(null, TO_TAG, suffix);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV1_WORD.substring(PREV1_WORD.length() - 4, PREV1_WORD.length())));
		assertFalse(matches(NEXT1_WORD.substring(NEXT1_WORD.length() - 4, NEXT1_WORD.length())));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(THIS_WORD.substring(THIS_WORD.length() - 4, THIS_WORD.length())));
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestUntaggedMatchability(SUFFIXRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(SUFFIXRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(SUFFIXRuleFactory.INSTANCE, 
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 1, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 2, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 3, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 4, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 5, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 6, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 7, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 8, THIS_WORD.length()));
	}
}
