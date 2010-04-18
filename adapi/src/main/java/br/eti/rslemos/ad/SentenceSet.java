package br.eti.rslemos.ad;

import java.util.Iterator;

public abstract class SentenceSet implements Iterable<Sentence>, Skippable {

	private final Iterator<Sentence> sentences;

	public SentenceSet(final ADCorpus corpus) {
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

	private static final class SentenceIterator extends BaseIterator<Sentence> {
		private final SentenceSet set;
		
		private SentenceIterator(SentenceSet set, ADCorpus corpus) {
			super(corpus);
			this.set = set;
		}

		@Override
		protected void tail() {
			set.sentencesTail(corpus);
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