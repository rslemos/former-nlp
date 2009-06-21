package br.eti.rslemos.ad;

import java.util.Iterator;

public abstract class SentencesContainer implements Iterable<Sentence> {

	private final Iterator<Sentence> sentences;

	public SentencesContainer(final ADCorpus corpus) {
		sentences = new Iterator<Sentence>() {
			private ADCorpus corpus0 = corpus;
			
			public boolean hasNext() {
				if (corpus0 == null)
					return false;

				if (corpus0.line.startsWith("<s")) {
					return true;
				} else {
					sentencesTail(corpus0);

					corpus0 = null;
					
					return false;
				}
			}

			public Sentence next() {
				return new Sentence(corpus0);
			}

			public void remove() {
			}
		};
	}

	public Iterator<Sentence> sentences() {
		return sentences;
	}
	
	public Iterator<Sentence> iterator() {
		return sentences();
	}

	protected abstract void sentencesTail(ADCorpus corpus);

	void skipOver() {
		while(sentences.hasNext())
			sentences.next().skipOver();
	}

}