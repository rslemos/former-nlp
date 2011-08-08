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

public class PREV2TAGRuleFactory extends AbstractSingleRuleFactory {
	public static final PREV2TAGRuleFactory INSTANCE = new PREV2TAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		Object tag_2 = context.getToken(-2).get(Token.POS);

		return createRule(from, to, tag_2);
	}
	
	public Rule createRule(Object from, Object to, Object prev2Object) {
		return new PREV2TAGRule(from, to, prev2Object);
	}

	private static class PREV2TAGRule extends AbstractRule {
		private final Object prev2Object;
	
		private PREV2TAGRule(Object from, Object to, Object prev2Object) {
			super(from, to);
			this.prev2Object = prev2Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			Object tag_2 = context.getToken(-2).get(Token.POS);
			
			return prev2Object != null ? prev2Object.equals(tag_2) : tag_2 == null;
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(prev2Object != null ? prev2Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			PREV2TAGRule other = (PREV2TAGRule) o;
			
			return prev2Object != null ? prev2Object.equals(other.prev2Object) : other.prev2Object == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 43;
			hashCode += prev2Object != null ? prev2Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prev2Object;
		}
	}
}