package br.eti.rslemos.ad;

public class Analysis {

	private final ADCorpus corpus;

	private final int index;

	private Node rootNode;

	Analysis(ADCorpus corpus) {
		this.corpus = corpus;
		
		assert corpus.line.startsWith("A");
		index = Integer.parseInt(corpus.line.substring("A".length()));
		
		corpus.readNextLine();
	}

	public int getIndex() {
		return index;
	}

	public Node tree() {
		if (rootNode == null) {
			if (corpus.line.contains("\t"))
				rootNode = new TerminalNode(corpus);
			else
				rootNode = new NonTerminalNode(corpus);
		}
		
		return rootNode;
	}

	void readAll() {
		tree();
	}

}
