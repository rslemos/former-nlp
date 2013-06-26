/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * Copyright 2013  Rodrigo Lemos
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

public class WDAND2BFRRuleBehavior {
	private boolean matches(String prev2Word, String word) {
		Context context = buildContext();
		
		Rule rule = WDAND2BFR.Factory.INSTANCE.createRule(THIS_TAG, TO_TAG, prev2Word, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV2_WORD, PREV4_WORD));
		assertFalse(matches(PREV2_WORD, PREV3_WORD));
		assertFalse(matches(PREV2_WORD, PREV2_WORD));
		assertFalse(matches(PREV2_WORD, PREV1_WORD));
		assertFalse(matches(PREV2_WORD, NEXT1_WORD));
		assertFalse(matches(PREV2_WORD, NEXT2_WORD));
		assertFalse(matches(PREV2_WORD, NEXT3_WORD));
		assertFalse(matches(PREV2_WORD, NEXT4_WORD));
		
		assertFalse(matches(PREV4_WORD, THIS_WORD));
		assertFalse(matches(PREV3_WORD, THIS_WORD));
		assertFalse(matches(PREV1_WORD, THIS_WORD));
		assertFalse(matches(THIS_WORD,  THIS_WORD));
		assertFalse(matches(NEXT1_WORD, THIS_WORD));
		assertFalse(matches(NEXT2_WORD, THIS_WORD));
		assertFalse(matches(NEXT3_WORD, THIS_WORD));
		assertFalse(matches(NEXT4_WORD, THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_WORD, THIS_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(WDAND2BFR.Factory.INSTANCE);
	}
	
	@Test
	public void shouldNotDependOnContextObject() {
		testDependency(WDAND2BFR.Factory.INSTANCE.createRule(THIS_TAG, THIS_TAG, PREV2_WORD, THIS_WORD), F, F, F, F, F, F, F, F);
	}

	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(WDAND2BFR.Factory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(WDAND2BFR.Factory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(WDAND2BFR.Factory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " WDAND2BFR " + PREV2_WORD + " " + THIS_WORD);
	}
}
