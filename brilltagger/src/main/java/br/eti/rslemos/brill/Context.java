package br.eti.rslemos.brill;

public class Context {
	private Token[] contents;
	private int pointer;
	
	public Context(Token[] contents) {
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
	
	public void advance() {
		pointer++;
	}

	public void reset() {
		pointer = 0;
	}

}
