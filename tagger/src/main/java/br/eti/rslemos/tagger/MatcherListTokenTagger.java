package br.eti.rslemos.tagger;

import java.io.Serializable;


public class MatcherListTokenTagger extends AbstractTokenTagger implements Serializable {

	private static final long serialVersionUID = -8533304632457176736L;

	protected static interface Matcher extends Serializable {
		boolean matches(String word);

		Tag getTag();
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
		String word = token.getWord().toLowerCase();

		for (Matcher matcher : matchers) {
			if (matcher.matches(word)) {
				token.setTag(matcher.getTag());
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
		private Tag tag;

		public SuffixMatcher() {
			this(null, null);
		}

		public SuffixMatcher(String suffix, Tag tag) {
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

		public Tag getTag() {
			return tag;
		}

		public void setTag(Tag tag) {
			this.tag = tag;
		}
	}

	public static class StringMatcher implements Matcher {

		private static final long serialVersionUID = -1027474386195913937L;
		
		private String word;
		private Tag tag;

		public StringMatcher() {
			this(null, null);
		}

		public StringMatcher(String word, Tag tag) {
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

		public Tag getTag() {
			return tag;
		}

		public void setTag(Tag tag) {
			this.tag = tag;
		}
	}
}