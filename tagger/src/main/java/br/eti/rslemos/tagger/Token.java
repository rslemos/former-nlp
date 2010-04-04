package br.eti.rslemos.tagger;

public interface Token {
	String getWord();
	
	Object getTag();
	Token setTag(Object tag);
}
