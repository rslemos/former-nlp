package br.eti.rslemos.ad;

public class TerminalNode extends Node {

	private final String text;

	TerminalNode(ADCorpus corpus) {
		super(corpus);
		
		String line = corpus.line;
		if (line.contains(":")) {
			assert line.contains("\t");
			
			text = line.substring(line.indexOf('\t') + 1);
		} else {
			text = line.substring(depth);
		}
		
		corpus.readNextLine();
	}

	public String getText() {
		return text;
	}

}
