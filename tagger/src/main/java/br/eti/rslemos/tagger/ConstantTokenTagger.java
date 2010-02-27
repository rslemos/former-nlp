package br.eti.rslemos.tagger;

import java.io.Serializable;

public class ConstantTokenTagger extends AbstractTokenTagger implements Serializable {

	private static final long serialVersionUID = 1432743370347430019L;
	
	private String tag;

	public ConstantTokenTagger() {
		this(null);
	}
	
	public ConstantTokenTagger(String tag) {
		this.tag = tag;
	}

	public void tag(Token token) {
		token.setTag(tag);
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
