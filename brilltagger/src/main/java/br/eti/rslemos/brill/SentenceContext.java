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
package br.eti.rslemos.brill;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.SentenceIndexOutOfBoundsException;
import br.eti.rslemos.tagger.Token;

public class SentenceContext implements Context {
	private static final class NullToken implements Token {
		public Object getFeature(String name) {
			return null;
		}

		public Token setFeature(String name, Object value) {
			throw new IllegalStateException("Can'Object set NULL token tag '" + name + "' to '" + value + "'");
		}

		public Map<String, Object> getFeatures() {
			return Collections.emptyMap();
		}
	}

	public static final Token NULL_TOKEN = new NullToken();
	
	private final Sentence contents;
	private int current;

	public SentenceContext(Sentence contents) {
		this.contents = contents;
		current = -1;
	}

	public Token getToken(int offset) {
		try {
			return contents.get(current+offset);
		} catch (SentenceIndexOutOfBoundsException e) {
			return NULL_TOKEN;
		}
	}

	@Override
	public SentenceContext clone() {
		try {
			return (SentenceContext) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Object#clone() threw CloneNotSupportedException", e);
		}
	}

	public boolean hasNext() {
		return current + 1 < contents.size();
	}

	public Token next() {
		// next() should advance BEFORE since we must
		// retain state AFTER invocation
		// (the just returned Token is the current one)
		try {
			return contents.get(++current);
		} catch (SentenceIndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		Token current = getToken(0);
		
		if (current == NULL_TOKEN)
			result.append("><");
		
		for (Token token : contents) {
			if (token == current)
				result.append(">");
			
			result.append(token.toString());
			
			if (token == current)
				result.append("<");
			
			result.append(" ");
		}
		
		result.setLength(result.length() - 1);
		
		return result.toString();
	}

	
}
