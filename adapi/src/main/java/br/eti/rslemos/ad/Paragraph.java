package br.eti.rslemos.ad;


public class Paragraph extends SentencesContainer {

	Paragraph(ADCorpus corpus) {
		super(corpus);
		
		assert corpus.line.equals("<p>");

		corpus.readNextLine();
	}

	@Override
	protected void sentencesTail() {
		assert "</p>".equals(corpus.line);
		corpus.readNextLine();
	}

}
