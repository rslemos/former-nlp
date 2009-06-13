package br.eti.rslemos.brill;


public class NumberTokenTagger extends ConstantTokenTagger {
	public NumberTokenTagger(String tag) {
		super(tag);
	}

	@Override
	public void tag(Token token) {
		char[] chars = token.getWord().toCharArray();
		
		for (char ch : chars) {
			if (!(Character.isDigit(ch) || ch == '.' || ch == ','))
				return;
		}
		
		super.tag(token);
	}
}