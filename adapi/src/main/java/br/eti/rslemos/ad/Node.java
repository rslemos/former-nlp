package br.eti.rslemos.ad;

import java.util.Arrays;
import java.util.Iterator;

public abstract class Node implements Iterable<Node>, Skippable {

	private final String function;
	private final String form;
	private final String[] info;
	protected final int depth;

	private Iterator<Node> children;

	Node(final ADCorpus corpus) {
		String line = corpus.line;
		
		int i = 0;
		while(i < line.length() && line.charAt(i) == '=')
			i++;
		
		depth = i-1;
		
		line = line.substring(depth);
		
		String[] parts;

		if (line.contains(":") && !line.startsWith(":")) {
			// X:n("consolação" <act> F S)	Consolação
			parts = line.split(":");
			
			corpus.assertBoolean(parts[0].length() > 0);
			function = parts[0];
			
			line = line.substring((function + ":").length());
			parts = line.split("[(\t]");
			
			form = parts[0];
			
			line = line.substring(form.length());
			if (line.length() > 0 && line.charAt(0) == '(') {
				String info_chunk = line.substring(1, line.indexOf(')'));
				info = info_chunk.split(" ");
			} else
				info = null;
		} else {
			function = null;
			form = null;
			info = null;
		}

		children = new NodeIterator(this, corpus);
	}

	public String getFunction() {
		return function;
	}

	public String getForm() {
		return form;
	}

	public String[] getInfo() {
		return info;
	}

	public int getDepth() {
		return depth;
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

	private static String buildDepthPrefix(int length) {
		char[] prefixChars = new char[length];
		Arrays.fill(prefixChars, '=');
		
		return new String(prefixChars);
	}

	private static class NodeIterator extends Analysis.RootNodeIterator {
		private final Node node;

		private NodeIterator(Node node, ADCorpus corpus) {
			super(corpus);
			this.node = node;
		}

		@Override
		protected boolean testForNext() {
			return corpus.line.startsWith(buildDepthPrefix(node.depth + 1)) && corpus.line.length() > (node.depth + 1);
		}
	}

}
