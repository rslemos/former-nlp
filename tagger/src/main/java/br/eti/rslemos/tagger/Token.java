package br.eti.rslemos.tagger;

public interface Token<T> {
	String getWord();
	
	T getTag();
	void setTag(T tag);
}
