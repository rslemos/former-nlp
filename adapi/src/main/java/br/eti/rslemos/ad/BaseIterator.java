package br.eti.rslemos.ad;

import java.util.Iterator;

public abstract class BaseIterator<T extends Skippable> implements Iterator<T> {
	protected ADCorpus corpus;
	private T lastElement;

	protected BaseIterator(ADCorpus corpus) {
		this.corpus = corpus;
	}

	public final boolean hasNext() {
		if (corpus == null)
			return false;
		
		skipLastElement();

		if (testForNext()) {
			return true;
		} else {
			tail();
			
			corpus = null;
			lastElement = null;
			
			return false;
		}
	}

	protected void tail() {
	}

	protected abstract boolean testForNext();

	public final T next() {
		skipLastElement();
		
		return lastElement = buildNext();
	}

	private void skipLastElement() {
		if (lastElement != null) {
			lastElement.skip();
			lastElement = null;
		}
	}

	protected abstract T buildNext();

	public final void remove() {
		throw new UnsupportedOperationException();
	}

}

interface Skippable {
	void skip();
}
