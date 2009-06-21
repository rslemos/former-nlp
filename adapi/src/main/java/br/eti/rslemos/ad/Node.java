package br.eti.rslemos.ad;

public abstract class Node {

	private final String function;
	private final String form;
	private final Info info;

	Node(ADCorpus corpus) {
		String line = corpus.line;
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

}
