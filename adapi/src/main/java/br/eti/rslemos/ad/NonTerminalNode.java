package br.eti.rslemos.ad;

public class NonTerminalNode extends Node {

	NonTerminalNode(ADCorpus corpus) {
		super(corpus);
		
		corpus.readNextLine();
	}

}
