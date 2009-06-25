package br.eti.rslemos.ad;

import java.util.Iterator;

public class Extract implements Iterable<Paragraph>, Skippable {

	private final int id;
	private final String cad;
	private final String sec;
	private final String sem;
	
	private final Title title;
	private final Iterator<Paragraph> paragraphs;

	Extract(final ADCorpus corpus) {
		assert corpus.line.startsWith("<ext ");
		
		// <ext id=1000 cad="Esporte" sec="des" sem="94a">
		// <ext id=1003 cad="Caderno Especial" sec="nd" sem="94a">
		
		String[] parts;
		
		parts = corpus.line.substring("<ext ".length()).split("=", 2);
		assert parts[0].equals("id");
		parts = parts[1].split(" ", 2);
		id = Integer.parseInt(parts[0]);
		
		parts = parts[1].split("=", 2);
		assert parts[0].equals("cad");
		parts = parts[1].split("\"", 3);
		assert parts[0].length() == 0;
		cad = parts[1];
		
		parts = parts[2].trim().split("=", 2);
		assert parts[0].equals("sec");
		parts = parts[1].split("\"", 3);
		assert parts[0].length() == 0;
		sec = parts[1];
		
		parts = parts[2].trim().split("=", 2);
		assert parts[0].equals("sem");
		parts = parts[1].split("\"", 4);
		assert parts[0].length() == 0;
		sem = parts[1];
		
		assert parts[2].equals(">");
		
		corpus.readNextLine();

		
		if (corpus.line.equals("<t>"))
			title = new Title(corpus);
		else
			title = null;
		
		paragraphs = new ParagraphIterator(corpus);

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
		return title;
	}

	public Iterator<Paragraph> paragraphs() {
		// skip title
		if (title != null)
			title.skip();
		
		return paragraphs;
	}

	public Iterator<Paragraph> iterator() {
		return paragraphs();
	}

	public void skip() {
		if (title != null)
			title.skip();

		while(paragraphs.hasNext())
			paragraphs.next().skip();
	}

	private static class ParagraphIterator extends BaseIterator<Paragraph> {
		private ParagraphIterator(ADCorpus corpus) {
			super(corpus);
		}

		@Override
		protected boolean testForNext() {
			return corpus.line.equals("<p>");
		}

		@Override
		protected Paragraph buildNext() {
			return new Paragraph(corpus);
		}
	}
}
