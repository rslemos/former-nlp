package br.eti.rslemos.ad;

import java.util.Iterator;

public class Extract {

	private final int id;
	private final String cad;
	private final String sec;
	private final String sem;
	
	private transient ADCorpus corpus;
	private Title title;

	Extract(ADCorpus corpus) {
		this.corpus = corpus;
		
		// <ext id=1000 cad="Esporte" sec="des" sem="94a">
		String[] parts = corpus.line.split(" ");

		assert "<ext".equals(parts[0]);

		assert parts[1].startsWith("id=");
		id = Integer.parseInt(parts[1].substring("id=".length()));
		
		assert parts[2].startsWith("cad=\"");
		assert parts[2].endsWith("\"");
		cad = parts[2].substring("cad=\"".length(), parts[2].length() - "\"".length());
		
		assert parts[3].startsWith("sec=\"");
		assert parts[3].endsWith("\"");
		sec = parts[3].substring("sec=\"".length(), parts[3].length() - "\"".length());
		
		assert parts[4].startsWith("sem=\"");
		assert parts[4].endsWith("\">");
		sem = parts[4].substring("sem=\"".length(), parts[4].length() - "\">".length());
		
		corpus.readNextLine();
	}

	public int getId() {
		return id;
	}

	public String getCad() {
		return cad;
	}

	public String getSec() {
		return sec;
	}

	public String getSem() {
		return sem;
	}

	public Title title() {
		if (title == null) {
			title = new Title(corpus);
		}
		
		return title;
	}

	public Iterator<Paragraph> paragraphs() {
		// skip title
		title().skipOver();
		
		return new Iterator<Paragraph>() {

			public boolean hasNext() {
				return corpus.line.equals("<p>");
			}

			public Paragraph next() {
				return new Paragraph(corpus);
			}

			public void remove() {
			}
			
		};
	}

}
