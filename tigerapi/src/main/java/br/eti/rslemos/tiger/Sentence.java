package br.eti.rslemos.tiger;

import java.util.List;

public interface Sentence {
	String getId();
	Graph getGraph();
	List<? extends Match> getMatches();
}