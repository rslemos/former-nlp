package br.eti.rslemos.tagger;

public class SuffixTagger extends ConstantTokenTagger {

	private static final long serialVersionUID = -4973038023566826266L;

	private String suffix;

	public SuffixTagger() {
		super();
	}

	public SuffixTagger(String suffix, String tag) {
		super(tag);
		this.suffix = suffix;
	}

	@Override
	public void tag(Token token) {
		if (token.getWord().endsWith(suffix))
			super.tag(token);
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
