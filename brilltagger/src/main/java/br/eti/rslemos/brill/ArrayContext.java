package br.eti.rslemos.brill;

import java.util.NoSuchElementException;

public class ArrayContext implements Context {
	private Token[] contents;
	private int pointer;

	public ArrayContext(Token[] contents) {
		this.contents = contents;
		pointer = -1;
	}

	protected void setContents(Token[] contents) {
		this.contents = contents;
		pointer = -1;
	}

	public Token getToken(int offset) {
		try {
			return contents[pointer+offset];
		} catch (ArrayIndexOutOfBoundsException e) {
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

	public void reset() {
		pointer = -1;
	}

	public boolean hasNext() {
		return pointer < contents.length - 1;
	}

	public Token next() {
		try {
			return contents[++pointer];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
