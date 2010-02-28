package br.eti.rslemos.brill;

import java.util.Iterator;

import br.eti.rslemos.tagger.Token;

public interface Context<T> extends Iterator<Token<T>>, Cloneable {
	Token<T> getToken(int offset);
	Context<T> clone();
}