package br.eti.rslemos.tiger;

import java.util.List;

public interface Body {
	List<? extends Sentence> getSentences();
}