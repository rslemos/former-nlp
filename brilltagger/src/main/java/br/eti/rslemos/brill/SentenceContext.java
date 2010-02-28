package br.eti.rslemos.brill;

import java.util.NoSuchElementException;

import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.SentenceIndexOutOfBoundsException;
import br.eti.rslemos.tagger.Token;

public class SentenceContext<T> implements Context<T> {
	private final Token<T> NULL_TOKEN = new Token<T>() {

		public T getTag() {
			return null;
		}

		public String getWord() {
			return null;
		}

		public void setTag(T tag) {
			throw new IllegalStateException("Can't set NULL token tag to '" + tag + "'");
		}
	};
	
	private final Sentence<T> contents;
	private int current;

	public SentenceContext(Sentence<T> contents) {
		this.contents = contents;
		current = -1;
	}

	public Token<T> getToken(int offset) {
		try {
			return contents.get(current+offset);
		} catch (SentenceIndexOutOfBoundsException e) {
			return NULL_TOKEN;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SentenceContext<T> clone() {
		try {
			return (SentenceContext<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Object#clone() threw CloneNotSupportedException", e);
		}
	}

	public boolean hasNext() {
		return current + 1 < contents.size();
	}

	public Token<T> next() {
		// next() should advance BEFORE since we must
		// retain state AFTER invocation
		// (the just returned Token is the current one)
		try {
			return contents.get(++current);
		} catch (SentenceIndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
