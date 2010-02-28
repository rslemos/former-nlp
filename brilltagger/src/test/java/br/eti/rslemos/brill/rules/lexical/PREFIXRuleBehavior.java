package br.eti.rslemos.brill.rules.lexical;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.lexical.PREFIXRule;

public class PREFIXRuleBehavior {
	private boolean matches(String prefix) {
		Context<String> context = buildUntaggedContext();
		
		Rule<String> rule = new PREFIXRule<String>(null, TO_TAG, prefix);
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
		createAndTestUntaggedMatchability(PREFIXRule.FACTORY1);
		createAndTestUntaggedMatchability(PREFIXRule.FACTORY2);
		createAndTestUntaggedMatchability(PREFIXRule.FACTORY3);
		createAndTestUntaggedMatchability(PREFIXRule.FACTORY4);
		createAndTestUntaggedMatchability(PREFIXRule.FACTORY5);
		createAndTestUntaggedMatchability(PREFIXRule.FACTORY6);
		createAndTestUntaggedMatchability(PREFIXRule.FACTORY7);
		createAndTestUntaggedMatchability(PREFIXRule.FACTORY8);
//		createAndTestUntaggedMatchability(PREFIXRule.FACTORY9);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREFIXRule.FACTORY1);
		createAndTestObjectSemantics(PREFIXRule.FACTORY2);
		createAndTestObjectSemantics(PREFIXRule.FACTORY3);
		createAndTestObjectSemantics(PREFIXRule.FACTORY4);
		createAndTestObjectSemantics(PREFIXRule.FACTORY5);
		createAndTestObjectSemantics(PREFIXRule.FACTORY6);
		createAndTestObjectSemantics(PREFIXRule.FACTORY7);
		createAndTestObjectSemantics(PREFIXRule.FACTORY8);
//		createAndTestObjectSemantics(PREFIXRule.FACTORY9);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(PREFIXRule.FACTORY1, 
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 1));
		createAndTestBrillText(PREFIXRule.FACTORY2, 
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 2));
		createAndTestBrillText(PREFIXRule.FACTORY3, 
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 3));
		createAndTestBrillText(PREFIXRule.FACTORY4, 
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 4));
		createAndTestBrillText(PREFIXRule.FACTORY5, 
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 5));
		createAndTestBrillText(PREFIXRule.FACTORY6, 
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 6));
		createAndTestBrillText(PREFIXRule.FACTORY7, 
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 7));
		createAndTestBrillText(PREFIXRule.FACTORY8, 
				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 8));
//		createAndTestBrillText(PREFIXRule.FACTORY9, 
//				TO_TAG + " PREFIX " + THIS_WORD.substring(0, 9));
	}
}
