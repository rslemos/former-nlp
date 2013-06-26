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

public class PREVWD {
	public static class Factory extends AbstractSingleRuleFactory<Rule> {
		public static final Factory INSTANCE = new Factory();
	
		@Override
		public Rule createRule(Object from, Object to, Context context) {
			String word_1 = (String) context.getToken(-1).get(Token.WORD);
	
			return createRule(from, to, word_1);
		}
		
		public Rule createRule(Object from, Object to, String prevWord) {
			return new Rule(from, to, prevWord);
		}
	
	}
	
	private static class Rule extends AbstractRule {
		private final String prevWord;
	
		private Rule(Object from, Object to, String prevWord) {
			super(from, to);
			this.prevWord = prevWord;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word_1 = (String) context.getToken(-1).get(Token.WORD);
			
			return prevWord != null ? prevWord.equals(word_1) : word_1 == null;
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			Rule other = (Rule) o;
			
			return prevWord != null ? prevWord.equals(other.prevWord) : other.prevWord == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 59;
			hashCode += prevWord != null ? prevWord.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prevWord;
		}
	}
}
