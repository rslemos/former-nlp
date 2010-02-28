package br.eti.rslemos.tagger;

import java.io.Serializable;


public class CompositeTagger<T> implements Tagger<T>, Serializable {

	private static final long serialVersionUID = -8583142580025744638L;

	private Tagger<T>[] taggers;

	@SuppressWarnings("unchecked")
	public CompositeTagger() {
		this(new Tagger[0]);
	}

	public CompositeTagger(Tagger<T>... taggers) {
		this.taggers = taggers;
	}

	@SuppressWarnings("unchecked")
	public void tag(Sentence<T> sentence) {
		Token<T>[] wrappedSentence = new Token[sentence.size()];
		
		for (int i = 0; i < sentence.size(); i++) {
			wrappedSentence[i] = new FilteringToken<T>(sentence.get(i));
		}
		
		DefaultSentence<T> wrappedSentence0 = new DefaultSentence<T>(wrappedSentence);

		for (Tagger<T> tagger : taggers) {
			tagger.tag(wrappedSentence0);
		}
	}
	
	public Tagger<T>[] getTaggers() {
		return taggers;
	}

	public void setTaggers(Tagger<T>[] taggers) {
		this.taggers = taggers;
	}

	private static final class FilteringToken<T> implements Token<T> {
		private final Token<T> token;
		private boolean alreadySet = false;

		private FilteringToken(Token<T> token) {
			this.token = token;
		}

		public T getTag() {
			return token.getTag();
		}

		public String getWord() {
			return token.getWord();
		}

		public void setTag(T tag) {
			if (!alreadySet) {
				token.setTag(tag);
				alreadySet = true;
			}
		}
	}


}
