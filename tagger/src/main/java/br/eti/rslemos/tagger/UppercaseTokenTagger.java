package br.eti.rslemos.tagger;


public class UppercaseTokenTagger extends ConstantTokenTagger {
	
	private static final long serialVersionUID = -3428754969530138564L;

	public UppercaseTokenTagger() {
		this(null);
	}

	public UppercaseTokenTagger(Tag tag) {
		super(tag);
	}

	@Override
	public void tag(Token token) {
		if (Character.isUpperCase(token.getWord().charAt(0)))
			super.tag(token);
	}
}