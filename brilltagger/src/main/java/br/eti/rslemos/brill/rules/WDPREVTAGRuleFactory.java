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

public class WDPREVTAGRuleFactory extends AbstractSingleRuleFactory {
	public static final WDPREVTAGRuleFactory INSTANCE = new WDPREVTAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = (String) context.getToken(0).get(Token.WORD);
		Object tag_1 = context.getToken(-1).get(Token.POS);

		return createRule(from, to, tag_1, word0);
	}
	
	public Rule createRule(Object from, Object to, Object prevObject, String word) {
		return new WDPREVTAGRule(from, to, prevObject, word);
	}

	private static class WDPREVTAGRule extends AbstractRule {
		private final String word;
		private final Object prevObject;
	
		private WDPREVTAGRule(Object from, Object to, Object prevObject, String word) {
			super(from, to);
			this.word = word;
			this.prevObject = prevObject;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word0 = (String) context.getToken(0).get(Token.WORD);
			Object tag_1 = context.getToken(-1).get(Token.POS);
			
			return (word != null ? word.equals(word0) : word0 == null) &&
				(prevObject != null ? prevObject.equals(tag_1) : tag_1 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(prevObject != null ? prevObject.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			WDPREVTAGRule other = (WDPREVTAGRule) o;
			
			return (word != null ? word.equals(other.word) : other.word == null) &&
				(prevObject != null ? prevObject.equals(other.prevObject) : other.prevObject == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 23;
			hashCode += word != null ? word.hashCode() : 0;
			hashCode *= 5;
			hashCode += prevObject != null ? prevObject.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prevObject + " " + word;
		}
	}
}