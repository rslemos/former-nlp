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

public class WDPREVTAGRuleBehavior {
	private boolean matches(Object prevObject, String word) {
		Context context = buildContext();
		
		Rule rule = WDPREVTAG.Factory.INSTANCE.createRule(THIS_TAG, TO_TAG, prevObject, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV1_TAG, PREV4_WORD));
		assertFalse(matches(PREV1_TAG, PREV3_WORD));
		assertFalse(matches(PREV1_TAG, PREV2_WORD));
		assertFalse(matches(PREV1_TAG, PREV1_WORD));
		assertFalse(matches(PREV1_TAG, NEXT1_WORD));
		assertFalse(matches(PREV1_TAG, NEXT2_WORD));
		assertFalse(matches(PREV1_TAG, NEXT3_WORD));
		assertFalse(matches(PREV1_TAG, NEXT4_WORD));
		
		assertFalse(matches(PREV4_TAG, THIS_WORD));
		assertFalse(matches(PREV3_TAG, THIS_WORD));
		assertFalse(matches(PREV2_TAG, THIS_WORD));
		assertFalse(matches(THIS_TAG,  THIS_WORD));
		assertFalse(matches(NEXT1_TAG, THIS_WORD));
		assertFalse(matches(NEXT2_TAG, THIS_WORD));
		assertFalse(matches(NEXT3_TAG, THIS_WORD));
		assertFalse(matches(NEXT4_TAG, THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV1_TAG, THIS_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(WDPREVTAG.Factory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(WDPREVTAG.Factory.INSTANCE.createRule(THIS_TAG, THIS_TAG, PREV1_TAG, THIS_WORD), F, F, F, T, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(WDPREVTAG.Factory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(WDPREVTAG.Factory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(WDPREVTAG.Factory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " WDPREVTAG " + PREV1_TAG + " " + THIS_WORD);
	}
}
