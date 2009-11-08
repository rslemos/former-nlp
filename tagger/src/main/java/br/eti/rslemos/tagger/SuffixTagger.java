package br.eti.rslemos.tagger;

public class SuffixTagger extends ConstantTokenTagger {

	private final String suffix;

	public SuffixTagger(String suffix, String tag) {
		super(tag);
		this.suffix = suffix;
	}

	@Override
	public void tag(Token token) {
		if (token.getWord().endsWith(suffix))
			super.tag(token);
	}

}
