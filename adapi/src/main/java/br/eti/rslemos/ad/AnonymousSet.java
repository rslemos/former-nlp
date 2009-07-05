package br.eti.rslemos.ad;

public class AnonymousSet extends SentenceSet {

	AnonymousSet(ADCorpus corpus) {
		super(corpus);
		corpus.assertLineStartsWith("<s");
	}

	@Override
	protected void sentencesTail(ADCorpus corpus) {
	}

}
