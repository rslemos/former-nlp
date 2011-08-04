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

public class WDAND2TAGBFRRuleBehavior {
	private boolean matches(Object prev2Object, String word) {
		Context context = buildContext();
		
		Rule rule = WDAND2TAGBFRRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG, prev2Object, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV2_TAG, PREV4_WORD));
		assertFalse(matches(PREV2_TAG, PREV3_WORD));
		assertFalse(matches(PREV2_TAG, PREV2_WORD));
		assertFalse(matches(PREV2_TAG, PREV1_WORD));
		assertFalse(matches(PREV2_TAG, NEXT1_WORD));
		assertFalse(matches(PREV2_TAG, NEXT2_WORD));
		assertFalse(matches(PREV2_TAG, NEXT3_WORD));
		assertFalse(matches(PREV2_TAG, NEXT4_WORD));
		
		assertFalse(matches(PREV4_TAG, THIS_WORD));
		assertFalse(matches(PREV3_TAG, THIS_WORD));
		assertFalse(matches(PREV1_TAG, THIS_WORD));
		assertFalse(matches(THIS_TAG,  THIS_WORD));
		assertFalse(matches(NEXT1_TAG, THIS_WORD));
		assertFalse(matches(NEXT2_TAG, THIS_WORD));
		assertFalse(matches(NEXT3_TAG, THIS_WORD));
		assertFalse(matches(NEXT4_TAG, THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_TAG, THIS_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(WDAND2TAGBFRRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(WDAND2TAGBFRRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG, PREV2_TAG, THIS_WORD), F, F, T, F, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(WDAND2TAGBFRRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(WDAND2TAGBFRRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(WDAND2TAGBFRRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " WDAND2TAGBFR " + PREV2_TAG + " " + THIS_WORD);
	}
}
