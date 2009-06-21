package br.eti.rslemos.ad;

import java.util.Iterator;

public abstract class SentencesContainer {

	protected final ADCorpus corpus;

	protected boolean closed;
	private Iterator<Sentence> sentences;

	public SentencesContainer(ADCorpus corpus) {
		this.corpus = corpus;
	}

	public Iterator<Sentence> sentences() {
		if (closed)
			return null;
		
		if (sentences == null) {
			sentences = new Iterator<Sentence>() {
	
				public boolean hasNext() {
					if (closed)
						return false;
	
					if (corpus.line.startsWith("<s")) {
						return true;
					} else {
						sentencesTail();
	
						closed = true;
	
						return false;
					}
				}
	
				public Sentence next() {
					return new Sentence(corpus);
				}
	
				public void remove() {
				}
			};
		}
		
		return sentences;
	}

	protected abstract void sentencesTail();

	void readAll() {
		if (!closed) {
			Iterator<Sentence> sentences = sentences();
			while(sentences.hasNext())
				sentences.next().readAll();
		}
	}

}