package br.eti.rslemos.tagger;


public class UppercaseTokenTagger extends ConstantTokenTagger {
	public UppercaseTokenTagger(String tag) {
		super(tag);
	}

	@Override
	public void tag(Token token) {
		if (Character.isUpperCase(token.getWord().charAt(0)))
			super.tag(token);
	}
}