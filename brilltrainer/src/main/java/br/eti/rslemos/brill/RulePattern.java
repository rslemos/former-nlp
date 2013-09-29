/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2013  Rodrigo Lemos
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
package br.eti.rslemos.brill;

import java.util.ArrayList;

public class RulePattern {
	@SuppressWarnings("unchecked")
	ArrayList<String>[] matches = new ArrayList[0];
	ArrayList<String> sets = new ArrayList<String>();
	
	public void addMatch(int real, String feature) {
		// 'real' can be either 0, positive or negative
		// let the positives occupy the 2*real th position
		// the negatives will take the -1 -> 1; -2 -> 3; real -> -2*real - 1
		int stored = (real >= 0) ? (2 * real) : (-2 * real - 1);
		if (!(matches.length > stored)) {
			@SuppressWarnings("unchecked")
			ArrayList<String>[] newmatches = new ArrayList[stored+1];
			System.arraycopy(matches, 0, newmatches, 0, matches.length);
			matches = newmatches;
		}
		
		if (matches[stored] == null)
			matches[stored] = new ArrayList<String>(2);
		
		matches[stored].add(feature);
	}

	public void addSet(int i, String feature) {
		if (i != 0)
			throw new IllegalArgumentException();
		
		sets.add(feature);
	}

}
