package br.eti.rslemos.brill;

public class ArrayContext implements Context {
	private Token[] contents;
	private int pointer;

	public ArrayContext(Token[] contents) {
		this.contents = contents;
	}

	protected void setContents(Token[] contents) {
		this.contents = contents;
	}

	public Token getToken(int i) {
		try {
			return contents[i+pointer];
		} catch (ArrayIndexOutOfBoundsException e) {
			return Token.NULL;
		}
	}

	public boolean isValidPosition() {
		return pointer < contents.length;
	}

	public void advance() {
		pointer++;
	}

	public void reset() {
		pointer = 0;
	}

}
