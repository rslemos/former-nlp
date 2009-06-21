package br.eti.rslemos.ad;

public class TerminalNode extends Node {

	private final String text;

	TerminalNode(ADCorpus corpus) {
		super(corpus);
		
		assert corpus.line.contains("\t");
		
		String line = corpus.line;
		text = line.substring(line.indexOf('\t') + 1);
		
		corpus.readNextLine();
	}

	public String getText() {
		return text;
	}

}
