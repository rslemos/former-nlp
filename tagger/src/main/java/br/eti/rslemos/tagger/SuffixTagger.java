package br.eti.rslemos.tagger;

public class SuffixTagger<T> extends ConstantTokenTagger<T> {

	private static final long serialVersionUID = -4973038023566826266L;

	private String suffix;

	public SuffixTagger() {
		super();
	}

	public SuffixTagger(String suffix, T tag) {
		super(tag);
		this.suffix = suffix;
	}

	@Override
	public void tag(Token<T> token) {
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
