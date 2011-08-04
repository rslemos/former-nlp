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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DefaultSentence implements Sentence {
	private final List<Token> sentence;

	public DefaultSentence(Token[] sentence) {
		this(Arrays.asList(sentence));
	}
	
	public DefaultSentence(List<Token> sentence) {
		this.sentence = sentence;
	}

	public DefaultSentence(Sentence sentence) {
		this.sentence = new ArrayList<Token>(sentence.size());
		for (Token token : sentence) {
			this.sentence.add(new DefaultToken(token));
		}
		
		((ArrayList<Token>)this.sentence).trimToSize();
	}

	public Iterator<Token> iterator() {
		return sentence.iterator();
	}

	public int size() {
		return sentence.size();
	}

	public Token get(int i) {
		try {
			return sentence.get(i);
		} catch (IndexOutOfBoundsException e) {
			throw new SentenceIndexOutOfBoundsException(i);
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("\"");
		
		for (Token token : sentence) {
			result.append(token.toString()).append(" ");
		}
		
		result.setLength(result.length() - 1);
		
		result.append("\"");
		
		return result.toString();
	}
	
	
}
