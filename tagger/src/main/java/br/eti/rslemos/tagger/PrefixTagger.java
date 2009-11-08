package br.eti.rslemos.tagger;

public class PrefixTagger extends ConstantTokenTagger {

	private final String prefix;

	public PrefixTagger(String prefix, String tag) {
		super(tag);
		this.prefix = prefix;
	}

	@Override
	public void tag(Token token) {
		if (token.getWord().startsWith(prefix))
			super.tag(token);
	}

}
