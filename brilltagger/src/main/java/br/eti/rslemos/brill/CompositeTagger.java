package br.eti.rslemos.brill;

import java.util.List;

public class CompositeTagger implements Tagger {

	private final List<Tagger> taggers;

	public CompositeTagger(List<Tagger> taggers) {
		this.taggers = taggers;
	}

	public void tag(Sentence sentence) {
		Token[] wrappedSentence = new Token[sentence.size()];
		
		for (int i = 0; i < sentence.size(); i++) {
			wrappedSentence[i] = new FilteringToken(sentence.get(i));
		}
		
		DefaultSentence wrappedSentence0 = new DefaultSentence(wrappedSentence);

		for (Tagger tagger : taggers) {
			tagger.tag(wrappedSentence0);
		}
	}

	private static final class FilteringToken implements Token {
		private final Token token;
		private boolean alreadySet = false;

		private FilteringToken(Token token) {
			this.token = token;
		}

		public String getTag() {
			return token.getTag();
		}

		public String getWord() {
			return token.getWord();
		}

		public void setTag(String tag) {
			if (!alreadySet) {
				token.setTag(tag);
				alreadySet = true;
			}
		}
	}


}
