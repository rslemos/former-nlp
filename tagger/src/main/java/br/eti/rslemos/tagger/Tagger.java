package br.eti.rslemos.tagger;

public interface Tagger<T> {

	void tag(Sentence<T> sentence);

}