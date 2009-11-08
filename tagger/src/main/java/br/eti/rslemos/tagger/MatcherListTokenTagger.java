package br.eti.rslemos.tagger;


public class MatcherListTokenTagger extends AbstractTokenTagger {

	protected static interface Matcher {
		boolean matches(String word);

		String getTag();
	}

	private final Matcher[] matchers;

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

	public static class SuffixMatcher implements Matcher {

		private final String suffix;
		private final String tag;

		public SuffixMatcher(String suffix, String tag) {
			this.suffix = suffix;
			this.tag = tag;
		}

		public boolean matches(String word) {
			return word.endsWith(suffix) && !word.equals(suffix);
		}

		public String getTag() {
			return tag;
		}
	}

	public static class StringMatcher implements Matcher {

		private final String word;
		private final String tag;

		public StringMatcher(String word, String tag) {
			this.word = word;
			this.tag = tag;
		}

		public boolean matches(String word) {
			return this.word.equalsIgnoreCase(word);
		}

		public String getTag() {
			return tag;
		}
	}
}