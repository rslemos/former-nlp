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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DefaultToken implements Token {

	private final Map<String, Object> features = new HashMap<String, Object>();

	public DefaultToken(String word) {
		features.put(Token.WORD, word);
	}

	public DefaultToken(Token token) {
		features.putAll(token.getFeatures());
	}

	public Object get(String name) {
		return features.get(name);
	}

	public DefaultToken put(String name, Object value) {
		features.put(name, value);
		return this;
	}

	public Map<String, Object> getFeatures() {
		return Collections.unmodifiableMap(features);
	}

	@Override
	public String toString() {
		return get(Token.WORD) + "/" + get(Token.POS);
	}

	
}
