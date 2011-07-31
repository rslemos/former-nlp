package br.eti.rslemos.tagger;

public abstract class AbstractToken implements Token {
	public static final String WORD = "word";
	public static final String POS = "POS";
	
	public String getWord() {
		return (String) getFeature(WORD);
	}

	public Object getTag() {
		return getFeature(POS);
	}

	public AbstractToken setTag(Object tag) {
		return (AbstractToken) setFeature(POS, tag);
	}
}
