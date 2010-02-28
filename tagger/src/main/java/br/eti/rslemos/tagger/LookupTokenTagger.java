package br.eti.rslemos.tagger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LookupTokenTagger<T> extends AbstractTokenTagger<T> implements Serializable {

	private static final long serialVersionUID = 6023313611881081572L;

	private Map<String, T> lexicon;

	public LookupTokenTagger() {
		this(new HashMap<String, T>());
	}

	public LookupTokenTagger(Map<String, T> lexicon) {
		this.lexicon = lexicon;
	}

	public void tag(Token<T> token) {
		String word = token.getWord();
		if (lexicon.containsKey(word))
			token.setTag(lexicon.get(word));
	}

	public Map<String, T> getLexicon() {
		return lexicon;
	}

	public void setLexicon(Map<String, T> lexicon) {
		this.lexicon = lexicon;
	}
}
