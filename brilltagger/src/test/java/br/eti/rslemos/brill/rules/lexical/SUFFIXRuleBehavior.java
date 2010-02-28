package br.eti.rslemos.brill.rules.lexical;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.lexical.SUFFIXRule;

public class SUFFIXRuleBehavior {
	private boolean matches(String suffix) {
		Context<String> context = buildUntaggedContext();
		
		Rule<String> rule = new SUFFIXRule<String>(null, TO_TAG, suffix);
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
	
//	@Test
//	public void shouldDependOnFromTag() {
//		createAndTestBasicUntaggedDependency(PREFIXRule.FACTORY);
//	}
//
//	@Test
//	public void shouldDependOnContextTag() {
//		createAndTestContextUntaggedIndependency(PREFIXRule.FACTORY);
//	}
//	
	@Test
	public void shouldCreateRule() {
		createAndTestUntaggedMatchability(SUFFIXRule.FACTORY1);
		createAndTestUntaggedMatchability(SUFFIXRule.FACTORY2);
		createAndTestUntaggedMatchability(SUFFIXRule.FACTORY3);
		createAndTestUntaggedMatchability(SUFFIXRule.FACTORY4);
		createAndTestUntaggedMatchability(SUFFIXRule.FACTORY5);
		createAndTestUntaggedMatchability(SUFFIXRule.FACTORY6);
		createAndTestUntaggedMatchability(SUFFIXRule.FACTORY7);
		createAndTestUntaggedMatchability(SUFFIXRule.FACTORY8);
//		createAndTestUntaggedMatchability(SUFFIXRule.FACTORY9);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(SUFFIXRule.FACTORY1);
		createAndTestObjectSemantics(SUFFIXRule.FACTORY2);
		createAndTestObjectSemantics(SUFFIXRule.FACTORY3);
		createAndTestObjectSemantics(SUFFIXRule.FACTORY4);
		createAndTestObjectSemantics(SUFFIXRule.FACTORY5);
		createAndTestObjectSemantics(SUFFIXRule.FACTORY6);
		createAndTestObjectSemantics(SUFFIXRule.FACTORY7);
		createAndTestObjectSemantics(SUFFIXRule.FACTORY8);
//		createAndTestObjectSemantics(SUFFIXRule.FACTORY9);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(SUFFIXRule.FACTORY1, 
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 1, THIS_WORD.length()));
		createAndTestBrillText(SUFFIXRule.FACTORY2, 
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 2, THIS_WORD.length()));
		createAndTestBrillText(SUFFIXRule.FACTORY3, 
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 3, THIS_WORD.length()));
		createAndTestBrillText(SUFFIXRule.FACTORY4, 
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 4, THIS_WORD.length()));
		createAndTestBrillText(SUFFIXRule.FACTORY5, 
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 5, THIS_WORD.length()));
		createAndTestBrillText(SUFFIXRule.FACTORY6, 
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 6, THIS_WORD.length()));
		createAndTestBrillText(SUFFIXRule.FACTORY7, 
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 7, THIS_WORD.length()));
		createAndTestBrillText(SUFFIXRule.FACTORY8, 
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 8, THIS_WORD.length()));
//		createAndTestBrillText(SUFFIXRule.FACTORY9, 
//				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 9, THIS_WORD.length()));
	}
}
