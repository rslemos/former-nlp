package br.eti.rslemos.ad;

import java.util.Iterator;

public abstract class SentencesContainer implements Iterable<Sentence>, Skippable {

	private final Iterator<Sentence> sentences;

	public SentencesContainer(final ADCorpus corpus) {
		sentences = new SentenceIterator(this, corpus);
	}

	public Iterator<Sentence> sentences() {
		return sentences;
	}
	
	public Iterator<Sentence> iterator() {
		return sentences();
	}

	protected abstract void sentencesTail(ADCorpus corpus);

	public void skip() {
		while(sentences.hasNext())
			sentences.next().skip();
	}

	private static class SentenceIterator extends BaseIterator<Sentence> {
		private final SentencesContainer container;
		
		private SentenceIterator(SentencesContainer container, ADCorpus corpus) {
			super(corpus);
			this.container = container;
		}

		@Override
		protected void tail() {
			container.sentencesTail(corpus);
		}

		@Override
		protected boolean testForNext() {
			return corpus.line.startsWith("<s");
		}

		@Override
		protected Sentence buildNext() {
			return new Sentence(corpus);
		}
	}
}