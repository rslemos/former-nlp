package br.eti.rslemos.tagger;


public interface Sentence extends Iterable<Token> {

	int size();

	Token get(int i) throws SentenceIndexOutOfBoundsException;

}
