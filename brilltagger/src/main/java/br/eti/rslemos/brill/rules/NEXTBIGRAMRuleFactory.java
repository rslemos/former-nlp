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

public class NEXTBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public static final NEXTBIGRAMRuleFactory INSTANCE = new NEXTBIGRAMRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word1 = (String) context.getToken(1).getFeature(Token.WORD);
		String word2 = (String) context.getToken(2).getFeature(Token.WORD);

		return createRule(from, to, word1, word2);
	}
	
	public Rule createRule(Object from, Object to, String next1Word, String next2Word) {
		return new NEXTBIGRAMRule(from, to, next1Word, next2Word);
	}

	private static class NEXTBIGRAMRule extends AbstractRule {
		private final String next1Word;
		private final String next2Word;
	
		private NEXTBIGRAMRule(Object from, Object to, String next1Word, String next2Word) {
			super(from, to);
			this.next1Word = next1Word;
			this.next2Word = next2Word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word1 = (String) context.getToken(1).getFeature(Token.WORD);
			String word2 = (String) context.getToken(2).getFeature(Token.WORD);
			
			return (next1Word != null ? next1Word.equals(word1) : word1 == null) &&
				(next2Word != null ? next2Word.equals(word2) : word2 == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			NEXTBIGRAMRule other = (NEXTBIGRAMRule) o;
			
			return (next1Word != null ? next1Word.equals(other.next1Word) : other.next1Word == null) &&
				(next2Word != null ? next2Word.equals(other.next2Word) : other.next2Word == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 11;
			hashCode += next1Word != null ? next1Word.hashCode() : 0;
			hashCode *= 5;
			hashCode += next2Word != null ? next2Word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + next1Word + " " + next2Word;
		}
	}
}