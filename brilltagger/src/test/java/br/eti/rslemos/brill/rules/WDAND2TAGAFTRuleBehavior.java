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

public class WDAND2TAGAFTRuleBehavior {
	private boolean matches(String word, Object next2Object) {
		Context context = buildContext();
		
		Rule rule = WDAND2TAGAFTRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG, word, next2Object);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD, NEXT2_TAG));
		assertFalse(matches(PREV3_WORD, NEXT2_TAG));
		assertFalse(matches(PREV2_WORD, NEXT2_TAG));
		assertFalse(matches(PREV1_WORD, NEXT2_TAG));
		assertFalse(matches(NEXT1_WORD, NEXT2_TAG));
		assertFalse(matches(NEXT2_WORD, NEXT2_TAG));
		assertFalse(matches(NEXT3_WORD, NEXT2_TAG));
		assertFalse(matches(NEXT4_WORD, NEXT2_TAG));
		
		assertFalse(matches(THIS_WORD, PREV4_TAG));
		assertFalse(matches(THIS_WORD, PREV3_TAG));
		assertFalse(matches(THIS_WORD, PREV2_TAG));
		assertFalse(matches(THIS_WORD, PREV1_TAG));
		assertFalse(matches(THIS_WORD,  THIS_TAG));
		assertFalse(matches(THIS_WORD, NEXT1_TAG));
		assertFalse(matches(THIS_WORD, NEXT3_TAG));
		assertFalse(matches(THIS_WORD, NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(THIS_WORD, NEXT2_TAG));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(WDAND2TAGAFTRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(WDAND2TAGAFTRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG, THIS_WORD, NEXT2_TAG), F, F, F, F, F, T, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(WDAND2TAGAFTRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(WDAND2TAGAFTRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(WDAND2TAGAFTRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " WDAND2TAGAFT " + THIS_WORD + " " + NEXT2_TAG);
	}
}
