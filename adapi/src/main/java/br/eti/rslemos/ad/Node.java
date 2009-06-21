package br.eti.rslemos.ad;

import java.util.Arrays;
import java.util.Iterator;

public abstract class Node {

	private final String function;
	private final String form;
	private final Info info;
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
				if ("n".equals(form))
					info = new Info_n(info_chunk);
				else if("pron-indef".equals(form))
					info = new Info_pron_indef(info_chunk);
				else if("prp".equals(form))
					info = new Info_prp(info_chunk);
				else if("prop".equals(form))
					info = new Info_prop(info_chunk);
				else
					throw new RuntimeException();
			} else
				info = null;
		} else {
			function = null;
			form = null;
			info = null;
		}

		children = new Iterator<Node>() {
			private ADCorpus corpus0 = corpus;
			
			public boolean hasNext() {
				if (corpus0 == null)
					return false;
				
				String prefix = buildDepthPrefix(depth + 1);
				if (corpus0.line.startsWith(prefix)) {
					return true;
				} else {
					corpus0 = null;
					
					return false;
				}
			}

			public Node next() {
				Node subNode;
				if (corpus0.line.contains("\t") || !corpus0.line.contains(":"))
					subNode = new TerminalNode(corpus0);
				else
					subNode = new NonTerminalNode(corpus0);

				return subNode;
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

	public Info getInfo() {
		return info;
	}

	public int getDepth() {
		return depth;
	}

	public Iterator<Node> getChildren() {
		return children;
	}

	private static String buildDepthPrefix(int length) {
		char[] prefixChars = new char[length];
		Arrays.fill(prefixChars, '=');
		
		return new String(prefixChars).intern();
	}

}
