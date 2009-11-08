package br.eti.rslemos.brill;

import java.util.NoSuchElementException;

public class SentenceContext implements Context {
	private Sentence contents;
	private int pointer;

	public SentenceContext(Sentence contents) {
		this.contents = contents;
		pointer = -1;
	}

	protected void setContents(Sentence contents) {
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
	public Context clone() {
		try {
			return (Context) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Object#clone() threw CloneNotSupportedException", e);
		}
	}

	protected void reset() {
		pointer = -1;
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