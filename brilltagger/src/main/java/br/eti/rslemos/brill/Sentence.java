package br.eti.rslemos.brill;


public interface Sentence extends Iterable<Token> {

	int size();

	Token get(int i) throws SentenceIndexOutOfBounds;

}
