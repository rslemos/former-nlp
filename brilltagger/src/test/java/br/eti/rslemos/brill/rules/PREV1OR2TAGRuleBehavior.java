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

public class PREV1OR2TAGRuleBehavior {
	private boolean matches(Object prev1or2Object) {
		Context context = buildContext();
		
		Rule rule = PREV1OR2TAG.Factory.INSTANCE.createRule(THIS_TAG, TO_TAG, prev1or2Object);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(PREV3_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT1_TAG));
		assertFalse(matches(NEXT2_TAG));
		assertFalse(matches(NEXT3_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_TAG));
		assertTrue(matches(PREV1_TAG));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(PREV1OR2TAG.Factory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(PREV1OR2TAG.Factory.INSTANCE.createRule(THIS_TAG, THIS_TAG, PREV1_TAG), F, F, F, T, F, F, F, F);
		testDependency(PREV1OR2TAG.Factory.INSTANCE.createRule(THIS_TAG, THIS_TAG, PREV2_TAG), F, F, T, F, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(PREV1OR2TAG.Factory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(PREV1OR2TAG.Factory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(PREV1OR2TAG.Factory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2TAG " + PREV1_TAG,
				THIS_TAG + " " + TO_TAG + " PREV1OR2TAG " + PREV2_TAG);
	}
}
