package br.eti.rslemos.tagger;

import java.util.Map;

public class LookupTokenTagger extends AbstractTokenTagger {

	private final Map<String, String> lexicon;

	public LookupTokenTagger(Map<String, String> lexicon) {
		this.lexicon = lexicon;
	}

	public void tag(Token token) {
		String word = token.getWord();
		if (lexicon.containsKey(word))
			token.setTag(lexicon.get(word));
	}

}
