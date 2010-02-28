package br.eti.rslemos.tagger;

import java.io.Serializable;


public class MatcherListTokenTagger<T> extends AbstractTokenTagger<T> implements Serializable {

	private static final long serialVersionUID = -8533304632457176736L;

	protected static interface Matcher<T1> extends Serializable {
		boolean matches(String word);

		T1 getTag();
	}

	private Matcher<T>[] matchers;

	@SuppressWarnings("unchecked")
	public MatcherListTokenTagger() {
		this(new Matcher[0]);
	}

	public MatcherListTokenTagger(Matcher<T>... matchers) {
		super();
		this.matchers = matchers;
	}

	@Override
	public void tag(Token<T> token) {
		String word = token.getWord().toLowerCase();

		for (Matcher<T> matcher : matchers) {
			if (matcher.matches(word)) {
				token.setTag(matcher.getTag());
				break;
			}
		}
	}

	
	public Matcher<T>[] getMatchers() {
		return matchers;
	}

	public void setMatchers(Matcher<T>[] matchers) {
		this.matchers = matchers;
	}

	public static class SuffixMatcher<T1> implements Matcher<T1> {

		private static final long serialVersionUID = 172788707169113015L;

		private String suffix;
		private T1 tag;

		public SuffixMatcher() {
			this(null, null);
		}

		public SuffixMatcher(String suffix, T1 tag) {
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

		public T1 getTag() {
			return tag;
		}

		public void setTag(T1 tag) {
			this.tag = tag;
		}
	}

	public static class StringMatcher<T1> implements Matcher<T1> {

		private static final long serialVersionUID = -1027474386195913937L;
		
		private String word;
		private T1 tag;

		public StringMatcher() {
			this(null, null);
		}

		public StringMatcher(String word, T1 tag) {
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

		public T1 getTag() {
			return tag;
		}

		public void setTag(T1 tag) {
			this.tag = tag;
		}
	}
}