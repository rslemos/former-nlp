package br.eti.rslemos.brill;

import java.util.Map;

public class LookupBaseTagger implements BaseTagger {

	private final Map<String, String> lexicon;

	public LookupBaseTagger(Map<String, String> lexicon) {
		this.lexicon = lexicon;
	}

	public void tag(Token token) {
		String word = token.getWord();
		if (lexicon.containsKey(word))
			token.setTag(lexicon.get(word));
	}

}
