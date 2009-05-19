package br.eti.rslemos.brill;

public class ConstantTokenTagger extends AbstractTokenTagger {

	private final String tag;

	public ConstantTokenTagger(String tag) {
		this.tag = tag;
	}

	public void tag(Token token) {
		token.setTag(tag);
	}

}