package br.eti.rslemos.tagger;


public interface Sentence<T> extends Iterable<Token<T>> {

	int size();

	Token<T> get(int i) throws SentenceIndexOutOfBoundsException;

}
