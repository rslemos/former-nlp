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

public class TerminalNode extends Node {

	private final String text;

	TerminalNode(ADCorpus corpus) {
		super(corpus);
		
		String line = corpus.line;
		if (line.contains(":") && !line.startsWith(":")) {
			corpus.assertLineContains("\t");
			
			text = line.substring(line.indexOf('\t') + 1);
		} else {
			text = line.substring(depth);
		}
		
		corpus.readNextLine();
	}

	public String getText() {
		return text;
	}

}
