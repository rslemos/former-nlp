package br.eti.rslemos.ad;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Extract implements Iterable<SentenceSet>, Skippable {

	private final Map<String, String> attributes = new LinkedHashMap<String, String>();
	
	private final SentenceSetIterator sentenceSets;

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
		
		sentenceSets = new SentenceSetIterator(corpus);

	}

	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	private Title title;
	private boolean gotTitle = false;
	
	public Title getFirstParagraphIfTitle() {
		if (!gotTitle) {
			gotTitle = true;
			if (sentenceSets.hasNext()) {
				SentenceSet next = sentenceSets.next();
				if (next instanceof Title)
					title = (Title)next;
				else
					sentenceSets.push(next);
			}
		}
		
		return title;
	}

	public Iterator<SentenceSet> sentenceSets() {
		// skip title
		if (getFirstParagraphIfTitle() != null)
			title.skip();
		
		return sentenceSets;
	}

	public Iterator<SentenceSet> iterator() {
		return sentenceSets();
	}

	public void skip() {
		if (getFirstParagraphIfTitle() != null)
			title.skip();

		while(sentenceSets.hasNext())
			sentenceSets.next().skip();
	}

	private static class SentenceSetIterator extends BaseIterator<SentenceSet> {
		private SentenceSet next;

		private SentenceSetIterator(ADCorpus corpus) {
			super(corpus);
		}

		void push(SentenceSet next) {
			this.next = next;
		}

		@Override
		protected boolean testForNext() {
			if (next != null)
				return true;
			
			return corpus.line.equals("<p>") || corpus.line.equals("<t>");
		}

		@Override
		protected SentenceSet buildNext() {
			if (next != null) {
				try {
					return next;
				} finally {
					next = null;
				}
			}
			
			if (corpus.line.equals("<p>")) {
				return new Paragraph(corpus);
			} else if (corpus.line.equals("<t>")) {
				return new Title(corpus);
			}
			
			throw new IllegalStateException();
		}
	}
}
