package br.eti.rslemos.tagger;

public class NumberTokenTagger<T> extends ConstantTokenTagger<T> {
	
	private static final long serialVersionUID = -3751732697281158495L;

	public NumberTokenTagger() {
		this(null);
	}

	public NumberTokenTagger(T tag) {
		super(tag);
	}

	@Override
	public void tag(Token<T> token) {
		char[] chars = token.getWord().toCharArray();
		
		for (char ch : chars) {
			if (!(Character.isDigit(ch) || ch == '.' || ch == ','))
				return;
		}
		
		super.tag(token);
	}
}