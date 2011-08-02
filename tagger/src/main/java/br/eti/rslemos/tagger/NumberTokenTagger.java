package br.eti.rslemos.tagger;

public class NumberTokenTagger extends ConstantTokenTagger {
	
	private static final long serialVersionUID = -3751732697281158495L;

	public NumberTokenTagger() {
		this(null);
	}

	public NumberTokenTagger(Object tag) {
		super(tag);
	}

	@Override
	public void tag(Token token) {
		char[] chars = ((String) token.getFeature(AbstractToken.WORD)).toCharArray();
		
		for (char ch : chars) {
			if (!(Character.isDigit(ch) || ch == '.' || ch == ','))
				return;
		}
		
		super.tag(token);
	}
}