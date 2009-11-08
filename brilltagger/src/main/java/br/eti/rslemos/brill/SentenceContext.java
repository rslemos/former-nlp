package br.eti.rslemos.brill;

import java.util.NoSuchElementException;

public class SentenceContext implements Context {
	private final Sentence contents;
	int pointer;

	public SentenceContext(Sentence contents) {
		this.contents = contents;
		pointer = -1;
	}

	public Token getToken(int offset) {
		try {
			return contents.get(pointer+offset);
		} catch (SentenceIndexOutOfBoundsException e) {
			return Token.NULL;
		}
	}

	@Override
	public SentenceContext clone() {
		try {
			return (SentenceContext) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Object#clone() threw CloneNotSupportedException", e);
		}
	}

	public boolean hasNext() {
		return pointer < contents.size() - 1;
	}

	public Token next() {
		try {
			return contents.get(++pointer);
		} catch (SentenceIndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
