package br.eti.rslemos.ad;

public class Analysis {

	private final int index;
	private final Node rootNode;

	Analysis(ADCorpus corpus) {
		assert corpus.line.startsWith("A");
		index = Integer.parseInt(corpus.line.substring("A".length()));
		
		corpus.readNextLine();

		if (corpus.line.contains("\t"))
			rootNode = new TerminalNode(corpus);
		else
			rootNode = new NonTerminalNode(corpus);
}

	public int getIndex() {
		return index;
	}

	public Node tree() {
		return rootNode;
	}

	void readAll() {
		tree();
	}

}
