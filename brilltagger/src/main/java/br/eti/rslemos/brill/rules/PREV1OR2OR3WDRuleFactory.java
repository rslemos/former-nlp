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

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public class PREV1OR2OR3WDRuleFactory extends AbstractRuleFactory {
	
	public static final PREV1OR2OR3WDRuleFactory INSTANCE = new PREV1OR2OR3WDRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		String word_1 = (String) context.getToken(-1).getFeature(Token.WORD);
		String word_2 = (String) context.getToken(-2).getFeature(Token.WORD);
		String word_3 = (String) context.getToken(-3).getFeature(Token.WORD);

		return Arrays.<Rule> asList(
				createRule(from, to, word_1), 
				createRule(from, to, word_2),
				createRule(from, to, word_3)
		);
	}
	
	public Rule createRule(Object from, Object to, String prev1or2or3Word) {
		return new PREV1OR2OR3WDRule(from, to, prev1or2or3Word);
	}

	private static class PREV1OR2OR3WDRule extends AbstractRule {
		private final String prev1or2or3Word;
	
		private PREV1OR2OR3WDRule(Object from, Object to, String prev1or2or3Word) {
			super(from, to);
			
			this.prev1or2or3Word = prev1or2or3Word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word_1 = (String) context.getToken(-1).getFeature(Token.WORD);
			String word_2 = (String) context.getToken(-2).getFeature(Token.WORD);
			String word_3 = (String) context.getToken(-3).getFeature(Token.WORD);
			
			return prev1or2or3Word != null 
			? (prev1or2or3Word.equals(word_1) | prev1or2or3Word.equals(word_2) | prev1or2or3Word.equals(word_3)) 
			: (word_1 == null | word_2 == null | word_3 == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			PREV1OR2OR3WDRule other = (PREV1OR2OR3WDRule) o;
			
			return prev1or2or3Word != null ? prev1or2or3Word.equals(other.prev1or2or3Word) : other.prev1or2or3Word == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 61;
			hashCode += prev1or2or3Word != null ? prev1or2or3Word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prev1or2or3Word;
		}
	}
}