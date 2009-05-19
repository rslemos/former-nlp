package br.eti.rslemos.brill;

import java.util.ArrayList;
import java.util.List;

public class CompositeTagger implements Tagger {

	private final List<Tagger> taggers;

	public CompositeTagger(List<Tagger> taggers) {
		this.taggers = taggers;
	}

	public void tagSentence(List<Token> sentence) {
		List<Token> wrappedSentence = new ArrayList<Token>(sentence.size());
		
		for (Token token : sentence)
			wrappedSentence.add(new FilteringToken(token));
		
		for (Tagger tagger : taggers) {
			tagger.tagSentence(wrappedSentence);
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
