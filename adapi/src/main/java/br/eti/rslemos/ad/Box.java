package br.eti.rslemos.ad;


public class Box extends SentenceSet {

	Box(ADCorpus corpus) {
		super(corpus);
		
		corpus.assertLineEquals("<caixa>");

		corpus.readNextLine();
	}

	@Override
	protected void sentencesTail(ADCorpus corpus) {
		corpus.assertLineEquals("</caixa>");
		corpus.readNextLine();
	}

}
