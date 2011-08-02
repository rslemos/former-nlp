package br.eti.rslemos.tagger;

import java.io.Serializable;

public class ConstantTokenTagger extends AbstractTokenTagger implements Serializable {

	private static final long serialVersionUID = 1432743370347430019L;
	
	private Object tag;

	public ConstantTokenTagger() {
		this(null);
	}
	
	public ConstantTokenTagger(Object tag) {
		this.tag = tag;
	}

	@Override
	public void tag(Token token) {
		token.setFeature(Token.POS, tag);
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}
}
