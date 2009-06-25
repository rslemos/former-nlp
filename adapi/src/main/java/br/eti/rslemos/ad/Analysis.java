package br.eti.rslemos.ad;

import java.util.Iterator;

public class Analysis implements Iterable<Node>, Skippable {

	private final int index;

	private Iterator<Node> children;

	Analysis(final ADCorpus corpus) {
		assert corpus.line.startsWith("A");
		index = Integer.parseInt(corpus.line.substring("A".length()));
		
		corpus.readNextLine();

		children = new RootNodeIterator(corpus);
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

	public void skip() {
		while(children.hasNext())
			children.next().skip();
	}

	static class RootNodeIterator extends BaseIterator<Node> {
		RootNodeIterator(ADCorpus corpus) {
			super(corpus);
		}

		@Override
		protected boolean testForNext() {
			return corpus.line.length() > 0;
		}

		@Override
		protected Node buildNext() {
			if (corpus.line.contains("\t") || !corpus.line.contains(":"))
				return new TerminalNode(corpus);
			else
				return new NonTerminalNode(corpus);
		}
	}

}
