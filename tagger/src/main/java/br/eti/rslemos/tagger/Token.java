package br.eti.rslemos.tagger;

public interface Token {
	String getWord();
	
	Object getTag();
	void setTag(Object tag);
}
