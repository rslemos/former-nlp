package br.eti.rslemos.tagger;

import java.io.Serializable;
import java.util.ArrayList;

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

	public void tag(Sentence<T> sentence) {
		sentence = wrapSentence(sentence);

		for (Tagger<T> tagger : taggers) {
			tagger.tag(sentence);
		}
	}

	private Sentence<T> wrapSentence(Sentence<T> sentence) {
		ArrayList<Token<T>> wrappedSentence = new ArrayList<Token<T>>(sentence.size());
		
		for (Token<T> token : sentence) {
			wrappedSentence.add(new FilteringToken<T>(token));
		}
		
		return new DefaultSentence<T>(wrappedSentence);
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
