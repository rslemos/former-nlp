package br.eti.rslemos.tiger;

public interface Corpus {
	String getId();
	String getVersion();
	Head getHead();
	Body getBody();
}