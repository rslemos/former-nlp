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
		createAndTestUntaggedMatchability(SUFFIXRule.<String>FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(SUFFIXRule.<String>FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(SUFFIXRule.<String>FACTORY(), 
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 1, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 2, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 3, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 4, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 5, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 6, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 7, THIS_WORD.length()),
				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 8, THIS_WORD.length()));
//		createAndTestBrillText(SUFFIXRule.FACTORY9, 
//				TO_TAG + " SUFFIX " + THIS_WORD.substring(THIS_WORD.length() - 9, THIS_WORD.length()));
	}
}
