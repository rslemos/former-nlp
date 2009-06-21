package br.eti.rslemos.ad;

import java.util.Arrays;
import java.util.Iterator;

public abstract class Node {

	private final ADCorpus corpus;

	private final String function;
	private final String form;
	private final Info info;
	private final int depth;

	Node(ADCorpus corpus) {
		this.corpus = corpus;
		
		String line = corpus.line;
		
		int i = 0;
		while(line.charAt(i++) == '=');
		depth = i-1;
		
		line = line.substring(depth);
		
		String[] parts;
		
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
		return new Iterator<Node>() {

			public boolean hasNext() {
				String prefix = buildDepthPrefix(depth + 1);
				return corpus.line.startsWith(prefix);
			}

			public Node next() {
				Node subNode;
				if (corpus.line.contains("\t"))
					subNode = new TerminalNode(corpus);
				else
					subNode = new NonTerminalNode(corpus);

				return subNode;
			}

			public void remove() {
			}
		};
	}

	private static String buildDepthPrefix(int length) {
		char[] prefixChars = new char[length];
		Arrays.fill(prefixChars, '=');
		
		return new String(prefixChars).intern();
	}

}
