package br.eti.rslemos.tagger;

public abstract class AbstractToken implements Token {
	public static final String WORD = "_text";
	public static final String POS = "_pos";
	
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
