package br.eti.rslemos.ad;

import java.util.Iterator;

public class Sentence implements Iterable<Analysis>, Skippable {

	private final String id;
	private final String ref;
	private final String source;
	private final String text;

	private final  Iterator<Analysis> analyses;

	Sentence(final ADCorpus corpus) {
		// <s id="1" ref="CF1000-1" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a">
		String[] parts = corpus.line.split(" ", 4);

		assert "<s".equals(parts[0]);
		
		assert parts[1].startsWith("id=\"");
		assert parts[1].endsWith("\"");
		id = parts[1].substring("id=\"".length(), parts[1].length() - "\"".length());
		
		assert parts[2].startsWith("ref=\"");
		assert parts[2].endsWith("\"");
		ref = parts[2].substring("ref=\"".length(), parts[2].length() - "\"".length());
		
		assert parts[3].startsWith("source=\"");
		assert parts[3].endsWith("\">");
		source = parts[3].substring("source=\"".length(), parts[3].length() - "\">".length());
		
		corpus.readNextLine();
		
		// SOURCE: ref="CF1000-1" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a"
		assert corpus.line.equals("SOURCE: ref=\"" + ref + "\" source=\"" + source + "\"");

		corpus.readNextLine();
		
		// CF1000-1 Consolação
		assert corpus.line.startsWith(ref + " ");
		
		text = corpus.line.substring((ref + " ").length());
		
		corpus.readNextLine();

		analyses = new AnalysisIterator(corpus);
	}

	public String getId() {
		return id;
	}

	public String getRef() {
		return ref;
	}

	public String getSource() {
		return source;
	}

	public String getText() {
		return text;
	}

	public Iterator<Analysis> analyses() {
		return analyses;
	}

	public Iterator<Analysis> iterator() {
		return analyses();
	}

	public void skip() {
		while(analyses.hasNext())
			analyses.next().skip();
	}

	private static class AnalysisIterator extends BaseIterator<Analysis> {
		private AnalysisIterator(ADCorpus corpus) {
			super(corpus);
		}

		@Override
		protected void tail() {
			assert corpus.line.length() == 0 : corpus.line;
			corpus.readNextLine();
			assert "</s>".equals(corpus.line);
			corpus.readNextLine();
		}

		@Override
		protected boolean testForNext() {
			return corpus.line.startsWith("A");
		}

		@Override
		protected Analysis buildNext() {
			return new Analysis(corpus);
		}
	}

}
