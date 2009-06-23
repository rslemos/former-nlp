package br.eti.rslemos.ad;

import java.util.Iterator;

public class Analysis implements Iterable<Node> {

	private final int index;

	private Iterator<Node> children;

	Analysis(final ADCorpus corpus) {
		assert corpus.line.startsWith("A");
		index = Integer.parseInt(corpus.line.substring("A".length()));
		
		corpus.readNextLine();

		children = new Iterator<Node>() {
			private ADCorpus corpus0 = corpus;
			private Node lastNode;

			public boolean hasNext() {
				if (corpus0 == null)
					return false;
				
				if (corpus0.line.length() > 0) {
					return true;
				} else {
					corpus0 = null;
					
					return false;
				}
			}

			public Node next() {
				if (corpus0.line.contains("\t") || !corpus0.line.contains(":"))
					lastNode = new TerminalNode(corpus0);
				else
					lastNode = new NonTerminalNode(corpus0);

				return lastNode;
			}

			public void remove() {
			}
		};
	}

	public int getIndex() {
		return index;
	}

	public Iterator<Node> children() {
		return children;
	}

	public Iterator<Node> iterator() {
		return children();
	}

	void skipOver() {
		while(children.hasNext())
			children.next().skipOver();
	}

}
