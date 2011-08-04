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

public class Analysis implements Iterable<Node>, Skippable {

	private final int index;

	private Iterator<Node> children;

	Analysis(final ADCorpus corpus) {
		corpus.assertLineStartsWith("A");
		index = Integer.parseInt(corpus.line.substring("A".length()));
		
		corpus.readNextLine();

		children = new RootNodeIterator(corpus);
	}

	public int getIndex() {
		return index;
	}

	public Iterator<Node> children() {
		return children;
	}

	public Iterator<Node> iterator() {
		return children();
	}

	public void skip() {
		while(children.hasNext())
			children.next().skip();
	}

	static class RootNodeIterator extends BaseIterator<Node> {
		RootNodeIterator(ADCorpus corpus) {
			super(corpus);
		}

		@Override
		protected boolean testForNext() {
			return corpus.line.length() > 0 && !corpus.line.equals("</s>");
		}

		@Override
		protected Node buildNext() {
			if (corpus.line.contains("\t") || !corpus.line.contains(":") || corpus.line.startsWith(":"))
				return new TerminalNode(corpus);
			else
				return new NonTerminalNode(corpus);
		}
	}

}
