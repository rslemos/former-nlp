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

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public class PREV2WDRuleFactory extends AbstractSingleRuleFactory {
	public static final PREV2WDRuleFactory INSTANCE = new PREV2WDRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word_2 = (String) context.getToken(-2).getFeature(Token.WORD);

		return createRule(from, to, word_2);
	}
	
	public Rule createRule(Object from, Object to, String prev2Word) {
		return new PREV2WDRule(from, to, prev2Word);
	}

	private static class PREV2WDRule extends AbstractRule {
		private final String prev2Word;
	
		private PREV2WDRule(Object from, Object to, String prev2Word) {
			super(from, to);
			this.prev2Word = prev2Word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word_2 = (String) context.getToken(-2).getFeature(Token.WORD);
			
			return prev2Word != null ? prev2Word.equals(word_2) : word_2 == null;
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			PREV2WDRule other = (PREV2WDRule) o;
			
			return prev2Word != null ? prev2Word.equals(other.prev2Word) : other.prev2Word == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 47;
			hashCode += prev2Word != null ? prev2Word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prev2Word;
		}
	}
}