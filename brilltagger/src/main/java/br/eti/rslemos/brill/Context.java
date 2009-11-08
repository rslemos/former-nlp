package br.eti.rslemos.brill;

import java.util.Iterator;

import br.eti.rslemos.tagger.Token;

public interface Context extends Iterator<Token>, Cloneable {
	Token getToken(int offset);
	Context clone();
}