/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
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
