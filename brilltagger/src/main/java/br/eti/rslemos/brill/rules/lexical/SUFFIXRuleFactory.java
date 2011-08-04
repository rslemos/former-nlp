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
package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Rule;

public class SUFFIXRuleFactory extends AbstractAFFIXRuleFactory {
	public static final SUFFIXRuleFactory INSTANCE = new SUFFIXRuleFactory();

	@Override
	protected Rule create(Object from, Object to, String word, int length) {
		return createRule(from, to, word.substring(word.length() - length, word.length()));
	}
	
	public Rule createRule(Object fromObject, Object toObject, String suffix) {
		return new SUFFIXRule(fromObject, toObject, suffix);
	}

	private static class SUFFIXRule extends AbstractLexicalBrillRule {
		private final String suffix;
	
		private SUFFIXRule(Object fromObject, Object toObject, String suffix) {
			super(fromObject, toObject);
			
			this.suffix = suffix;
		}
	
		@Override
		protected boolean thisMatches(String word0) {
			return suffix != null ? word0.endsWith(suffix) : word0 == null;
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			SUFFIXRule other = (SUFFIXRule) o;
			
			return suffix != null ? suffix.equals(other.suffix) : other.suffix == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 103;
			hashCode += suffix != null ? suffix.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + suffix;
		}
	
	}
}