package br.eti.rslemos.tiger;

import java.util.List;

public interface Graph {
	String getRoot();
	Boolean getDiscontinuous();
	List<? extends Terminal> getTerminals();
	List<? extends NonTerminal> getNonTerminals();
}