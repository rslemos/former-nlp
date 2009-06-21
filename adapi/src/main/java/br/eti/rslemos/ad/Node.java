package br.eti.rslemos.ad;

import java.util.Arrays;
import java.util.Iterator;

public abstract class Node {

	private final String function;
	private final String form;
	private final String[] info;
	protected final int depth;

	private Iterator<Node> children;

	Node(final ADCorpus corpus) {
		String line = corpus.line;
		
		int i = 0;
		while(line.charAt(i++) == '=');
		depth = i-1;
		
		line = line.substring(depth);
		
		String[] parts;

		if (line.contains(":")) {
			// X:n("consolação" <act> F S)	Consolação
			parts = line.split(":");
			
			assert parts[0].length() > 0;
			function = parts[0];
			
			line = line.substring((function + ":").length());
			parts = line.split("[(\t]");
			
			assert parts[0].length() > 0;
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

		children = new Iterator<Node>() {
			private ADCorpus corpus0 = corpus;
			private Node lastNode;
			
			public boolean hasNext() {
				if (corpus0 == null)
					return false;
				
				String prefix = buildDepthPrefix(depth + 1);
				if (corpus0.line.startsWith(prefix)) {
					return true;
				} else {
					corpus0 = null;
					lastNode = null;
					
					return false;
				}
			}

			public Node next() {
				if (lastNode != null)
					lastNode.skipOver();
				
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

	public Iterator<Node> getChildren() {
		return children;
	}

	void skipOver() {
		while(children.hasNext())
			children.next().skipOver();
	}

	private static String buildDepthPrefix(int length) {
		char[] prefixChars = new char[length];
		Arrays.fill(prefixChars, '=');
		
		return new String(prefixChars).intern();
	}

}
