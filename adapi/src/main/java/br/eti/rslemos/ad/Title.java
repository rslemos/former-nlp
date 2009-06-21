package br.eti.rslemos.ad;


public class Title extends SentencesContainer {

	Title(ADCorpus corpus) {
		super(corpus);
		
		assert corpus.line.equals("<t>");

		corpus.readNextLine();
	}

	@Override
	protected void sentencesTail(ADCorpus corpus) {
		assert "</t>".equals(corpus.line);
		corpus.readNextLine();
	}
}
