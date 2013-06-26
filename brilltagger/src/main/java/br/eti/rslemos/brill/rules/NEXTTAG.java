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

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.tagger.Token;

public class NEXTTAG {
	public static class Factory extends AbstractSingleRuleFactory<Rule> {
		public static final Factory INSTANCE = new Factory();
	
		@Override
		public Rule createRule(Object from, Object to, Context context) {
			Object tag1 = context.getToken(1).get(Token.POS);
	
			return createRule(from, to, tag1);
		}
		
		public Rule createRule(Object from, Object to, Object nextObject) {
			return new Rule(from, to, nextObject);
		}
	
	}
	
	private static class Rule extends AbstractRule {
		private final Object nextObject;
	
		private Rule(Object from, Object to, Object nextObject) {
			super(from, to);
			
			this.nextObject = nextObject;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			Object tag1 = context.getToken(1).get(Token.POS);
			
			return nextObject != null ? nextObject.equals(tag1) : tag1 == null;
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(nextObject != null ? nextObject.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			Rule other = (Rule) o;
			
			return nextObject != null ? nextObject.equals(other.nextObject) : other.nextObject == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 23;
			hashCode += nextObject != null ? nextObject.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + nextObject;
		}
	}
}
