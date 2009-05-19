package br.eti.rslemos.brill;

import java.util.List;

public interface Tagger {

	void tagSentence(List<Token> sentence);

}