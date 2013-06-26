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

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.tagger.Token;

public class PREV1OR2OR3TAG {
	public static class Factory extends AbstractRuleFactory<Rule> {
		
		public static final Factory INSTANCE = new Factory();
	
		@Override
		public Collection<Rule> create(Object from, Object to, Context context) {
			Object tag_1 = context.getToken(-1).get(Token.POS);
			Object tag_2 = context.getToken(-2).get(Token.POS);
			Object tag_3 = context.getToken(-3).get(Token.POS);
	
			return Arrays.<Rule> asList(
					createRule(from, to, tag_1), 
					createRule(from, to, tag_2),
					createRule(from, to, tag_3)
			);
		}
		
		public Rule createRule(Object from, Object to, Object prev1or2or3Object) {
			return new Rule(from, to, prev1or2or3Object);
		}
	
	}
	
	private static class Rule extends AbstractRule {
		private final Object prev1or2or3Object;
	
		private Rule(Object from, Object to, Object prev1or2or3Object) {
			super(from, to);
			
			this.prev1or2or3Object = prev1or2or3Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			Object tag_1 = context.getToken(-1).get(Token.POS);
			Object tag_2 = context.getToken(-2).get(Token.POS);
			Object tag_3 = context.getToken(-3).get(Token.POS);
			
			return prev1or2or3Object != null 
			? (prev1or2or3Object.equals(tag_1) | prev1or2or3Object.equals(tag_2) | prev1or2or3Object.equals(tag_3)) 
			: (tag_1 == null | tag_2 == null | tag_3 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(prev1or2or3Object != null ? prev1or2or3Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			Rule other = (Rule) o;
			
			return prev1or2or3Object != null ? prev1or2or3Object.equals(other.prev1or2or3Object) : other.prev1or2or3Object == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 31;
			hashCode += prev1or2or3Object != null ? prev1or2or3Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prev1or2or3Object;
		}
	}
}
