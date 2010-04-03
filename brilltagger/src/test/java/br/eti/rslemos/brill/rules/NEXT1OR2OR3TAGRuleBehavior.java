package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class NEXT1OR2OR3TAGRuleBehavior {
	private boolean matches(Tag next1or2or3Tag) {
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
		createAndTestBasicDependency(NEXT1OR2OR3TAGRule.FACTORY());
	}
	
	@Test
	public void shouldDependOnContextTag() {
		testDependency(new NEXT1OR2OR3TAGRule(THIS_TAG, THIS_TAG, NEXT1_TAG), F, F, F, F, T, F, F, F);
		testDependency(new NEXT1OR2OR3TAGRule(THIS_TAG, THIS_TAG, NEXT2_TAG), F, F, F, F, F, T, F, F);
		testDependency(new NEXT1OR2OR3TAGRule(THIS_TAG, THIS_TAG, NEXT3_TAG), F, F, F, F, F, F, T, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXT1OR2OR3TAGRule.FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXT1OR2OR3TAGRule.FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(NEXT1OR2OR3TAGRule.FACTORY(), 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3TAG " + NEXT1_TAG,
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3TAG " + NEXT2_TAG,
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3TAG " + NEXT3_TAG);
	}
}
