package br.eti.rslemos.tiger;

import java.util.Iterator;

public interface Body {
	Iterator<? extends Sentence> sentences();
}