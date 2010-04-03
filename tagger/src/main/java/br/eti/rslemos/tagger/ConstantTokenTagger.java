package br.eti.rslemos.tagger;

import java.io.Serializable;

public class ConstantTokenTagger extends AbstractTokenTagger implements Serializable {

	private static final long serialVersionUID = 1432743370347430019L;
	
	private Tag tag;

	public ConstantTokenTagger() {
		this(null);
	}
	
	public ConstantTokenTagger(Tag tag) {
		this.tag = tag;
	}

	public void tag(Token token) {
		token.setTag(tag);
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
}
