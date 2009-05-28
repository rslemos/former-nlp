package br.eti.rslemos.brill;

public interface Context {
	Token getToken(int offset);
	boolean isValidPosition();
	void advance();
	void reset();
}