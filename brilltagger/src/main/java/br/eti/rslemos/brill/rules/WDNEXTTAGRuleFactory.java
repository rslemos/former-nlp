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

public class WDNEXTTAGRuleFactory extends AbstractSingleRuleFactory {
	public static final WDNEXTTAGRuleFactory INSTANCE = new WDNEXTTAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = (String) context.getToken(0).get(Token.WORD);
		Object tag1 = context.getToken(1).get(Token.POS);

		return createRule(from, to, word0, tag1);
	}
	
	public Rule createRule(Object from, Object to, String word, Object next1Object) {
		return new WDNEXTTAGRule(from, to, word, next1Object);
	}

	private static class WDNEXTTAGRule extends AbstractRule {
		private final String word;
		private final Object next1Object;
	
		private WDNEXTTAGRule(Object from, Object to, String word, Object next1Object) {
			super(from, to);
			this.word = word;
			this.next1Object = next1Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word0 = (String) context.getToken(0).get(Token.WORD);
			Object tag1 = context.getToken(1).get(Token.POS);
			
			return (word != null ? word.equals(word0) : word0 == null) &&
				(next1Object != null ? next1Object.equals(tag1) : tag1 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(next1Object != null ? next1Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			WDNEXTTAGRule other = (WDNEXTTAGRule) o;
			
			return (word != null ? word.equals(other.word) : other.word == null) &&
				(next1Object != null ? next1Object.equals(other.next1Object) : other.next1Object == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 19;
			hashCode += word != null ? word.hashCode() : 0;
			hashCode *= 13;
			hashCode += next1Object != null ? next1Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + word + " " + next1Object;
		}
	}
}