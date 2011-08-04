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