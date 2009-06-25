package br.eti.rslemos.ad;


public class Title extends SentencesContainer {

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
