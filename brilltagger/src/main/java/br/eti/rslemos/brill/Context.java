package br.eti.rslemos.brill;

import java.util.Iterator;

public interface Context extends Iterator<Token>, Cloneable {
	Token getToken(int offset);
	Context clone();
}