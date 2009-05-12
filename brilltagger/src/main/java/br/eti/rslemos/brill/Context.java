package br.eti.rslemos.brill;

public class Context {
	private Token[] contents;
	private int pointer;
	
	protected Context(Token[] contents) {
		this.contents = contents;
	}

	public Token getToken(int i) {
		try {
			return contents[i+pointer];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public void advance() {
		pointer++;
	}
}
