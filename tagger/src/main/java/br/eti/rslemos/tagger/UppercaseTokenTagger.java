package br.eti.rslemos.tagger;


public class UppercaseTokenTagger<T> extends ConstantTokenTagger<T> {
	
	private static final long serialVersionUID = -3428754969530138564L;

	public UppercaseTokenTagger() {
		this(null);
	}

	public UppercaseTokenTagger(T tag) {
		super(tag);
	}

	@Override
	public void tag(Token<T> token) {
		if (Character.isUpperCase(token.getWord().charAt(0)))
			super.tag(token);
	}
}