package br.eti.rslemos.tagger;

public final class DefaultToken<T> implements Token<T> {

	private final String word;
	private T tag;

	public DefaultToken(String word) {
		this.word = word;
	}

	public DefaultToken(Token<T> token) {
		this.word = token.getWord();
		this.tag = token.getTag();
	}

	public String getWord() {
		return word;
	}

	public T getTag() {
		return tag;
	}

	public void setTag(T tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return word + "/" + tag;
	}

	
}
