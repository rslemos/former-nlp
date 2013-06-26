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

public class WDAND2TAGAFT {
	public static class Factory extends AbstractSingleRuleFactory<Rule> {
		public static final Factory INSTANCE = new Factory();
	
		@Override
		public Rule createRule(Object from, Object to, Context context) {
			String word0 = (String) context.getToken(0).get(Token.WORD);
			Object tag2 = context.getToken(2).get(Token.POS);
	
			return createRule(from, to, word0, tag2);
		}
		
		public Rule createRule(Object from, Object to, String word, Object next2Object) {
			return new Rule(from, to, word, next2Object);
		}
	
	}
	
	private static class Rule extends AbstractRule {
		private final String word;
		private final Object next2Object;
	
		private Rule(Object from, Object to, String word, Object next2Object) {
			super(from, to);
			this.word = word;
			this.next2Object = next2Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word0 = (String) context.getToken(0).get(Token.WORD);
			Object tag2 = context.getToken(2).get(Token.POS);
			
			return (word != null ? word.equals(word0) : word0 == null) &&
				(next2Object != null ? next2Object.equals(tag2) : tag2 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(next2Object != null ? next2Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			Rule other = (Rule) o;
			
			return (word != null ? word.equals(other.word) : other.word == null) &&
				(next2Object != null ? next2Object.equals(other.next2Object) : other.next2Object == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 19;
			hashCode += word != null ? word.hashCode() : 0;
			hashCode *= 7;
			hashCode += next2Object != null ? next2Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + word + " " + next2Object;
		}
	}
}
