package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3OR4WDRuleBehavior {
	private boolean matches(String next1or2or3or4Word) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new NEXT1OR2OR3OR4WDRule<String>(THIS_TAG, TO_TAG, next1or2or3or4Word);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD));
		assertFalse(matches(PREV3_WORD));
		assertFalse(matches(PREV2_WORD));
		assertFalse(matches(PREV1_WORD));
		assertFalse(matches(THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT1_WORD));
		assertTrue(matches(NEXT2_WORD));
		assertTrue(matches(NEXT3_WORD));
		assertTrue(matches(NEXT4_WORD));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(NEXT1OR2OR3OR4WDRule.<String>FACTORY1());
		createAndTestBasicDependency(NEXT1OR2OR3OR4WDRule.<String>FACTORY2());
		createAndTestBasicDependency(NEXT1OR2OR3OR4WDRule.<String>FACTORY3());
		createAndTestBasicDependency(NEXT1OR2OR3OR4WDRule.<String>FACTORY4());
	}
	
	@Test
	public void shouldDependOnContextTag() {
		createAndTestContextIndependency(NEXT1OR2OR3OR4WDRule.<String>FACTORY1());
		createAndTestContextIndependency(NEXT1OR2OR3OR4WDRule.<String>FACTORY2());
		createAndTestContextIndependency(NEXT1OR2OR3OR4WDRule.<String>FACTORY3());
		createAndTestContextIndependency(NEXT1OR2OR3OR4WDRule.<String>FACTORY4());
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXT1OR2OR3OR4WDRule.<String>FACTORY1());
		createAndTestMatchability(NEXT1OR2OR3OR4WDRule.<String>FACTORY2());
		createAndTestMatchability(NEXT1OR2OR3OR4WDRule.<String>FACTORY3());
		createAndTestMatchability(NEXT1OR2OR3OR4WDRule.<String>FACTORY4());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXT1OR2OR3OR4WDRule.<String>FACTORY1());
		createAndTestObjectSemantics(NEXT1OR2OR3OR4WDRule.<String>FACTORY2());
		createAndTestObjectSemantics(NEXT1OR2OR3OR4WDRule.<String>FACTORY3());
		createAndTestObjectSemantics(NEXT1OR2OR3OR4WDRule.<String>FACTORY4());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(NEXT1OR2OR3OR4WDRule.<String>FACTORY1(), 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT1_WORD);
		createAndTestBrillText(NEXT1OR2OR3OR4WDRule.<String>FACTORY2(), 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT2_WORD);
		createAndTestBrillText(NEXT1OR2OR3OR4WDRule.<String>FACTORY3(), 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT3_WORD);
		createAndTestBrillText(NEXT1OR2OR3OR4WDRule.<String>FACTORY4(), 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT4_WORD);
	}
}
