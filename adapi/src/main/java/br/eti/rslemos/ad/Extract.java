package br.eti.rslemos.ad;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Extract implements Iterable<SentenceSet>, Skippable {

	private final Map<String, String> attributes = new LinkedHashMap<String, String>();
	
	private final Iterator<SentenceSet> sentenceSets;

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
	
	public Iterator<SentenceSet> sentenceSets() {
		return sentenceSets;
	}

	public Iterator<SentenceSet> iterator() {
		return sentenceSets();
	}

	public void skip() {
		while(sentenceSets.hasNext())
			sentenceSets.next().skip();
	}

	private static class SentenceSetIterator extends BaseIterator<SentenceSet> {
		private SentenceSetIterator(ADCorpus corpus) {
			super(corpus);
		}

		@Override
		protected boolean testForNext() {
			return corpus.line.equals("<p>") || corpus.line.equals("<t>") || corpus.line.startsWith("<s") || corpus.line.equals("<caixa>");
		}

		@Override
		protected SentenceSet buildNext() {
			if (corpus.line.equals("<p>")) {
				return new Paragraph(corpus);
			} else if (corpus.line.equals("<t>")) {
				return new Title(corpus);
			} else if (corpus.line.startsWith("<s")) {
				return new AnonymousSet(corpus);
			} else if (corpus.line.equals("<caixa>")) {
				return new Box(corpus);
			}
			
			throw new IllegalStateException();
		}
	}
}
