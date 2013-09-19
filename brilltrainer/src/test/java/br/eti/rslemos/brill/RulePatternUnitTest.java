/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
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
package br.eti.rslemos.brill;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;

public class RulePatternUnitTest {
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSetIndex() {
		RulePattern pattern = new RulePattern();
		pattern.addSet(1, "POS");
	}
	
	@Test
	public void testBuildRule() {
		RulePattern pattern = new RulePattern();
		
		pattern.addMatch(-1, "POS");
		pattern.addMatch(0, "WORD");
		pattern.addMatch(1, "POS");
		pattern.addMatch(2, "POS");
		pattern.addSet(0, "POS");
		
		assertThat(rule.matches.length, is(equalTo(5)));
		assertThat(rule.matches[0], is(equalTo(Collections.singletonSet("WORD"))));
		assertThat(rule.matches[1], is(equalTo(Collections.singletonSet("POS"))));
		assertThat(rule.matches[2], is(equalTo(Collections.singletonSet("POS"))));
		assertThat(rule.matches[3], is(nullValue(Set.class));
		assertThat(rule.matches[4], is(equalTo(Collections.singletonSet("POS"))));
	}
}
