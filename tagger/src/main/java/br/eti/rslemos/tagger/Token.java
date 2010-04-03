package br.eti.rslemos.tagger;

public interface Token {
	String getWord();
	
	Tag getTag();
	void setTag(Tag tag);
}
