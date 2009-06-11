package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3TAGRuleBehavior {
	private boolean matches(String next1or2or3Tag) {
		Context context = buildContext();
		
		Rule rule = new NEXT1OR2OR3TAGRule(THIS_TAG, TO_TAG, next1or2or3Tag);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(PREV3_TAG));
		assertFalse(matches(PREV2_TAG));
		assertFalse(matches(PREV1_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT1_TAG));
		assertTrue(matches(NEXT2_TAG));
		assertTrue(matches(NEXT3_TAG));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(NEXT1OR2OR3TAGRule.FACTORY1);
		createAndTestBasicDependency(NEXT1OR2OR3TAGRule.FACTORY2);
		createAndTestBasicDependency(NEXT1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldDependOnContextTag() {
		createAndTestContextDependency(NEXT1OR2OR3TAGRule.FACTORY1, F, F, F, F, T, F, F, F);
		createAndTestContextDependency(NEXT1OR2OR3TAGRule.FACTORY2, F, F, F, F, F, T, F, F);
		createAndTestContextDependency(NEXT1OR2OR3TAGRule.FACTORY3, F, F, F, F, F, F, T, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXT1OR2OR3TAGRule.FACTORY1);
		createAndTestMatchability(NEXT1OR2OR3TAGRule.FACTORY2);
		createAndTestMatchability(NEXT1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXT1OR2OR3TAGRule.FACTORY1);
		createAndTestObjectSemantics(NEXT1OR2OR3TAGRule.FACTORY2);
		createAndTestObjectSemantics(NEXT1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(NEXT1OR2OR3TAGRule.FACTORY1, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3TAG " + NEXT1_TAG);
		createAndTestBrillText(NEXT1OR2OR3TAGRule.FACTORY2, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3TAG " + NEXT2_TAG);
		createAndTestBrillText(NEXT1OR2OR3TAGRule.FACTORY3, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3TAG " + NEXT3_TAG);
	}
}
