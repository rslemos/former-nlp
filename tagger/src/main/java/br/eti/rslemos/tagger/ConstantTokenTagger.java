package br.eti.rslemos.tagger;

import java.io.Serializable;

public class ConstantTokenTagger<T> extends AbstractTokenTagger<T> implements Serializable {

	private static final long serialVersionUID = 1432743370347430019L;
	
	private T tag;

	public ConstantTokenTagger() {
		this(null);
	}
	
	public ConstantTokenTagger(T tag) {
		this.tag = tag;
	}

	public void tag(Token<T> token) {
		token.setTag(tag);
	}

	public T getTag() {
		return tag;
	}

	public void setTag(T tag) {
		this.tag = tag;
	}
}
