package br.eti.rslemos.ad;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Extract implements Iterable<Paragraph>, Skippable {

	private final Map<String, String> attributes = new LinkedHashMap<String, String>();
	
	private final Title title;
	private final Iterator<Paragraph> paragraphs;

	Extract(final ADCorpus corpus) {
		corpus.assertLineStartsWith("<ext ");
		
		// <ext id=1000 cad="Esporte" sec="des" sem="94a">
		// <ext id=1003 cad="Caderno Especial" sec="nd" sem="94a">
		// <ext id="1001.porto-poesia=removeme=-2 a poesia toma porto-alegre=removeme=">
		
		String line = corpus.line.substring("<ext ".length()).trim();
		
		while(!line.equals(">")) {
			String[] parts;
			
			parts = line.split("=", 2);

			String name = parts[0];
			String value;
			if (parts[1].startsWith("\"")) {
				parts = parts[1].split("\"", 3);
				value = parts[1];
				line = parts[2].trim();
			} else {
				parts = parts[1].split(" ", 2);
				value = parts[0];
				line = parts[1].trim();
			}
			
			attributes.put(name.intern(), value);
		}
		
		corpus.readNextLine();

		
		if (corpus.line.equals("<t>"))
			title = new Title(corpus);
		else
			title = null;
		
		paragraphs = new ParagraphIterator(corpus);

	}

	public Map<String, String> getAttributes() {
		return attributes;
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
