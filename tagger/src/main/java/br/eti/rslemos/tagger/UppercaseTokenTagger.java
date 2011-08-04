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
package br.eti.rslemos.tagger;


public class UppercaseTokenTagger extends ConstantTokenTagger {
	
	private static final long serialVersionUID = -3428754969530138564L;

	public UppercaseTokenTagger() {
		this(null);
	}

	public UppercaseTokenTagger(Object tag) {
		super(tag);
	}

	@Override
	public void tag(Token token) {
		if (Character.isUpperCase(((String) token.getFeature(Token.WORD)).charAt(0)))
			super.tag(token);
	}
}