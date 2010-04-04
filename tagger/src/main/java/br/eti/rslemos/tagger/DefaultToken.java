package br.eti.rslemos.tagger;

public final class DefaultToken implements Token {

	private final String word;
	private Object tag;

	public DefaultToken(String word) {
		this.word = word;
	}

	public DefaultToken(Token token) {
		this.word = token.getWord();
		this.tag = token.getTag();
	}

	public String getWord() {
		return word;
	}

	public Object getTag() {
		return tag;
	}

	public DefaultToken setTag(Object tag) {
		this.tag = tag;
		return this;
	}

	@Override
	public String toString() {
		return word + "/" + tag;
	}

	
}
