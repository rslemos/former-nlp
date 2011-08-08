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

public class NEXT2TAGRuleFactory extends AbstractSingleRuleFactory {
	public static final NEXT2TAGRuleFactory INSTANCE = new NEXT2TAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		Object tag2 = context.getToken(2).get(Token.POS);

		return createRule(from, to, tag2);
	}
	
	public Rule createRule(Object from, Object to, Object next2Object) {
		return new NEXT2TAGRule(from, to, next2Object);
	}

	private static class NEXT2TAGRule extends AbstractRule {
		private final Object next2Object;
	
		private NEXT2TAGRule(Object from, Object to, Object next2Object) {
			super(from, to);
			
			this.next2Object = next2Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			Object tag2 = context.getToken(2).get(Token.POS);
			
			return next2Object != null ? next2Object.equals(tag2) : tag2 == null;
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
			
			NEXT2TAGRule other = (NEXT2TAGRule) o;
			
			return next2Object != null ? next2Object.equals(other.next2Object) : other.next2Object == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 17;
			hashCode += next2Object != null ? next2Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + next2Object;
		}
	}
}