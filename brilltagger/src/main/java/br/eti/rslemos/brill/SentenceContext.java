package br.eti.rslemos.brill;

import java.util.NoSuchElementException;

public class SentenceContext implements Context {
	private final Sentence contents;
	private int current;

	public SentenceContext(Sentence contents) {
		this.contents = contents;
		current = -1;
	}

	public Token getToken(int offset) {
		try {
			return contents.get(current+offset);
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
		return current + 1 < contents.size();
	}

	public Token next() {
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
