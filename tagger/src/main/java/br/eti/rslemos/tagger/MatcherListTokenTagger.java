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

import java.io.Serializable;


public class MatcherListTokenTagger extends AbstractTokenTagger implements Serializable {

	private static final long serialVersionUID = -8533304632457176736L;

	public interface Matcher extends Serializable {
		boolean matches(String word);

		Object getObject();
	}

	private Matcher[] matchers;

	public MatcherListTokenTagger() {
		this(new Matcher[0]);
	}

	public MatcherListTokenTagger(Matcher... matchers) {
		super();
		this.matchers = matchers;
	}

	@Override
	public void tag(Token token) {
		String word = ((String) token.getFeature(Token.WORD)).toLowerCase();

		for (Matcher matcher : matchers) {
			if (matcher.matches(word)) {
				token.setFeature(Token.POS, matcher.getObject());
				break;
			}
		}
	}

	
	public Matcher[] getMatchers() {
		return matchers;
	}

	public void setMatchers(Matcher[] matchers) {
		this.matchers = matchers;
	}

	public static class SuffixMatcher implements Matcher {

		private static final long serialVersionUID = 172788707169113015L;

		private String suffix;
		private Object tag;

		public SuffixMatcher() {
			this(null, null);
		}

		public SuffixMatcher(String suffix, Object tag) {
			this.suffix = suffix;
			this.tag = tag;
		}

		public boolean matches(String word) {
			return word.endsWith(suffix) && !word.equals(suffix);
		}

		public String getSuffix() {
			return suffix;
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}

		public Object getObject() {
			return tag;
		}

		public void setObject(Object tag) {
			this.tag = tag;
		}
	}

	public static class StringMatcher implements Matcher {

		private static final long serialVersionUID = -1027474386195913937L;
		
		private String word;
		private Object tag;

		public StringMatcher() {
			this(null, null);
		}

		public StringMatcher(String word, Object tag) {
			this.word = word;
			this.tag = tag;
		}

		public boolean matches(String word) {
			return this.word.equalsIgnoreCase(word);
		}

		public String getWord() {
			return word;
		}

		public void setWord(String word) {
			this.word = word;
		}

		public Object getObject() {
			return tag;
		}

		public void setObject(Object tag) {
			this.tag = tag;
		}
	}
}