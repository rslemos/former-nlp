package br.eti.rslemos.brill;

import java.util.Iterator;

public interface Context extends Iterator<Token> {
	Token getToken(int offset);
	void reset();
}