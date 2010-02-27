package br.eti.rslemos.tagger;

import java.io.Serializable;


public class CompositeTagger implements Tagger, Serializable {

	private static final long serialVersionUID = -8583142580025744638L;

	private Tagger[] taggers;

	public CompositeTagger() {
		this(new Tagger[0]);
	}

	public CompositeTagger(Tagger... taggers) {
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
	
	public Tagger[] getTaggers() {
		return taggers;
	}

	public void setTaggers(Tagger[] taggers) {
		this.taggers = taggers;
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
