package br.eti.rslemos.tagger;

public class PrefixTagger extends ConstantTokenTagger {

	private static final long serialVersionUID = 3824061986799338079L;

	private String prefix;

	public PrefixTagger() {
		this(null, null);
	}

	public PrefixTagger(String prefix, Object tag) {
		super(tag);
		this.prefix = prefix;
	}

	@Override
	public void tag(Token token) {
		if (((String) token.getFeature(Token.WORD)).startsWith(prefix))
			super.tag(token);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
