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

public class NEXT2WDRuleFactory extends AbstractSingleRuleFactory {
	public static final NEXT2WDRuleFactory INSTANCE = new NEXT2WDRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word2 = (String) context.getToken(2).get(Token.WORD);

		return createRule(from, to, word2);
	}
	
	public Rule createRule(Object from, Object to, String next2Word) {
		return new NEXT2WDRule(from, to, next2Word);
	}

	private static class NEXT2WDRule extends AbstractRule {
		private final String next2Word;
	
		private NEXT2WDRule(Object from, Object to, String next2Word) {
			super(from, to);
			
			this.next2Word = next2Word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word2 = (String) context.getToken(2).get(Token.WORD);
			
			return next2Word != null ? next2Word.equals(word2) : word2 == null;
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			NEXT2WDRule other = (NEXT2WDRule) o;
			
			return next2Word != null ? next2Word.equals(other.next2Word) : other.next2Word == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 19;
			hashCode += next2Word != null ? next2Word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + next2Word;
		}
	}
}