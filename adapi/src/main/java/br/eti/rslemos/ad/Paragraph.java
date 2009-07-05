package br.eti.rslemos.ad;


public class Paragraph extends SentenceSet {

	Paragraph(ADCorpus corpus) {
		super(corpus);
		
		corpus.assertLineEquals("<p>");

		corpus.readNextLine();
	}

	@Override
	protected void sentencesTail(ADCorpus corpus) {
		corpus.assertLineEquals("</p>");
		corpus.readNextLine();
	}

}
