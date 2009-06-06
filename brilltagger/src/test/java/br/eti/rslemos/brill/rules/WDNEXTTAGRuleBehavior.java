package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDNEXTTAGRuleBehavior {
	private boolean matches(String word, String next1Tag) {
		Context context = buildContext();
		
		Rule rule = new WDNEXTTAGRule(THIS_TAG, TO_TAG, word, next1Tag);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD, NEXT1_TAG));
		assertFalse(matches(PREV3_WORD, NEXT1_TAG));
		assertFalse(matches(PREV2_WORD, NEXT1_TAG));
		assertFalse(matches(PREV1_WORD, NEXT1_TAG));
		assertFalse(matches(NEXT1_WORD, NEXT1_TAG));
		assertFalse(matches(NEXT2_WORD, NEXT1_TAG));
		assertFalse(matches(NEXT3_WORD, NEXT1_TAG));
		assertFalse(matches(NEXT4_WORD, NEXT1_TAG));
		
		assertFalse(matches(THIS_WORD, PREV4_TAG));
		assertFalse(matches(THIS_WORD, PREV3_TAG));
		assertFalse(matches(THIS_WORD, PREV2_TAG));
		assertFalse(matches(THIS_WORD, PREV1_TAG));
		assertFalse(matches(THIS_WORD,  THIS_TAG));
		assertFalse(matches(THIS_WORD, NEXT2_TAG));
		assertFalse(matches(THIS_WORD, NEXT3_TAG));
		assertFalse(matches(THIS_WORD, NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(THIS_WORD, NEXT1_TAG));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(WDNEXTTAGRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(WDNEXTTAGRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleFactoryBehaviorUtils.createAndTestBrillText(WDNEXTTAGRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " WDNEXTTAG " + THIS_WORD + " " + NEXT1_TAG);
	}
}
