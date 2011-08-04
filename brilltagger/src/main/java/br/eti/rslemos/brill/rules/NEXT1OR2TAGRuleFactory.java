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

public class NEXT1OR2TAGRuleFactory extends AbstractRuleFactory {
	
	public static final NEXT1OR2TAGRuleFactory INSTANCE = new NEXT1OR2TAGRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		Object tag1 = context.getToken(1).getFeature(Token.POS);
		Object tag2 = context.getToken(2).getFeature(Token.POS);

		return Arrays.<Rule> asList(
				createRule(from, to, tag1),
				createRule(from, to, tag2)
		);
	}
	
	public Rule createRule(Object from, Object to, Object next1or2Object) {
		return new NEXT1OR2TAGRule(from, to, next1or2Object);
	}

	private static class NEXT1OR2TAGRule extends AbstractRule {
		private final Object next1or2Object;
	
		private NEXT1OR2TAGRule(Object from, Object to, Object next1or2Object) {
			super(from, to);
			
			this.next1or2Object = next1or2Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			Object tag1 = context.getToken(1).getFeature(Token.POS);
			Object tag2 = context.getToken(2).getFeature(Token.POS);
			
			return next1or2Object != null 
			? (next1or2Object.equals(tag1) | next1or2Object.equals(tag2)) 
			: (tag1 == null | tag2 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(next1or2Object != null ? next1or2Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			NEXT1OR2TAGRule other = (NEXT1OR2TAGRule) o;
			
			return next1or2Object != null ? next1or2Object.equals(other.next1or2Object) : other.next1or2Object == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 11;
			hashCode += next1or2Object != null ? next1or2Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + next1or2Object;
		}
	}
}