package br.eti.rslemos.brill;

public class ConstantBaseTagger implements BaseTagger {

	private final String tag;

	public ConstantBaseTagger(String tag) {
		this.tag = tag;
	}

	public void tag(Token token) {
		token.setTag(tag);
	}

}
