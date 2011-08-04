/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3OR4WDRuleBehavior {
	private boolean matches(String next1or2or3or4Word) {
		Context context = buildContext();
		
		Rule rule = NEXT1OR2OR3OR4WDRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG,
				next1or2or3or4Word);
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
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(NEXT1OR2OR3OR4WDRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(NEXT1OR2OR3OR4WDRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG,
				NEXT1_WORD), F, F, F, F, F, F, F, F);
		testDependency(NEXT1OR2OR3OR4WDRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG,
				NEXT2_WORD), F, F, F, F, F, F, F, F);
		testDependency(NEXT1OR2OR3OR4WDRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG,
				NEXT3_WORD), F, F, F, F, F, F, F, F);
		testDependency(NEXT1OR2OR3OR4WDRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG,
				NEXT4_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXT1OR2OR3OR4WDRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXT1OR2OR3OR4WDRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(NEXT1OR2OR3OR4WDRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT1_WORD,
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT2_WORD,
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT3_WORD,
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3OR4WD " + NEXT4_WORD);
	}
}
