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

public class PREFIXRuleFactory extends AbstractAFFIXRuleFactory {
	public static final PREFIXRuleFactory INSTANCE = new PREFIXRuleFactory();

	@Override
	protected Rule create(Object from, Object to, String word, int length) {
		return createRule(from, to, word.substring(0, length));
	}
	
	public Rule createRule(Object fromObject, Object toObject, String prefix) {
		return new PREFIXRule(fromObject, toObject, prefix);
	}

	private static class PREFIXRule extends AbstractLexicalBrillRule {
		private final String prefix;
	
		private PREFIXRule(Object fromObject, Object toObject, String prefix) {
			super(fromObject, toObject);
			
			this.prefix = prefix;
		}
	
		@Override
		protected boolean thisMatches(String word0) {
			return prefix != null ? word0.startsWith(prefix) : word0 == null;
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			PREFIXRule other = (PREFIXRule) o;
			
			return prefix != null ? prefix.equals(other.prefix) : other.prefix == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 101;
			hashCode += prefix != null ? prefix.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prefix;
		}
	
	}
}