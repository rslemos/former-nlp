package br.eti.rslemos.tagger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LookupTokenTagger extends AbstractTokenTagger implements Serializable {

	private static final long serialVersionUID = 6023313611881081572L;

	private Map<String, Object> lexicon;

	public LookupTokenTagger() {
		this(new HashMap<String, Object>());
	}

	public LookupTokenTagger(Map<String, Object> lexicon) {
		this.lexicon = lexicon;
	}

	@Override
	public void tag(Token token) {
		String word = (String) token.getFeature(Token.WORD);
		if (lexicon.containsKey(word))
			token.setFeature(Token.POS, lexicon.get(word));
	}

	public Map<String, Object> getLexicon() {
		return lexicon;
	}

	public void setLexicon(Map<String, Object> lexicon) {
		this.lexicon = lexicon;
	}
}
