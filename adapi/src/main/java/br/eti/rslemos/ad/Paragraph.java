package br.eti.rslemos.ad;


public class Paragraph extends SentencesContainer {

	Paragraph(ADCorpus corpus) {
		super(corpus);
		
		assert corpus.line.equals("<p>");

		corpus.readNextLine();
	}

	@Override
	protected void sentencesTail(ADCorpus corpus) {
		assert "</p>".equals(corpus.line) : "Expected '</p>'; found '" + corpus.line + "' (at line " + corpus.lineNumber + ")";
		corpus.readNextLine();
	}

}
