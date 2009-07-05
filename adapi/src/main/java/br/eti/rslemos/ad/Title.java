package br.eti.rslemos.ad;


public class Title extends SentenceSet {

	Title(ADCorpus corpus) {
		super(corpus);
		
		corpus.assertLineEquals("<t>");

		corpus.readNextLine();
	}

	@Override
	protected void sentencesTail(ADCorpus corpus) {
		corpus.assertLineEquals("</t>");
		corpus.readNextLine();
	}
}
