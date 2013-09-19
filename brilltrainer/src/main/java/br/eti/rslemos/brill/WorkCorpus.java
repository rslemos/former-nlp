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

import java.util.Arrays;
import java.util.Formatter;
import java.util.TreeSet;
import java.util.Map.Entry;

import br.eti.rslemos.tagger.Corpus;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

class WorkCorpus {
	Object[][] features;
	String[] featureNames;
	
	private int[] from;
	private int[] to;
	
	public WorkCorpus(Corpus corpus) {
		alloc(corpus, null);
		fill(corpus);
	}
	
	public WorkCorpus(Corpus corpus, WorkCorpus copy) {
		alloc(corpus, copy.featureNames);
		fill(corpus);
	}

	private final void alloc(Corpus corpus, String[] featureNames) {
		int tokens = 0;
		int sentences = 0;
		
		TreeSet<String> featureNamesSet = new TreeSet<String>();
		
		for (Sentence sentence : corpus) {
			sentences++;
			for (Token token : sentence) {
				tokens++;
				featureNamesSet.addAll(token.keySet());
			}
		}
		
		this.featureNames = featureNames == null ? featureNamesSet.toArray(new String[featureNamesSet.size()]) : featureNames;
		
		this.features = new Object[tokens][this.featureNames.length];
		int[][] area = new int[2][sentences]; // verify whether the are contiguous
		this.from = area[0];
		this.to = area[1];
	}
	
	private final void fill(Corpus corpus) {
		int i = 0, j = 0;
		
		for (Sentence sentence : corpus) {
			from[i] = j;
			for (Token token : sentence) {
				for (Entry<String, Object> entry : token.entrySet()) {
					int idx = Arrays.binarySearch(featureNames, entry.getKey());
					if (idx >= 0)
						features[j][idx] = entry.getValue();
				}
				j++;
			}
			to[i++] = j;
		}
	}

	public int tokens() {
		return features.length;
	}
	
	public String toString() {
		@SuppressWarnings("resource")
		Formatter result = new Formatter();
		
		result.format("features: %s\n", Arrays.toString(featureNames));
		result.format("%d sentences follow:\n", from.length);
		
		int j = 0;
		for (int i = 0; i < features.length; i++) {
			if (Arrays.binarySearch(from, i) >= 0)
				result.format("%5d. ", ++j);
			else
				result.format("       ");
			
			result.format("%s\n", Arrays.toString(features[i]));
		}
		
		return result.toString();
	}

	public int[] getSentenceForToken(int i) {
		int j = Arrays.binarySearch(from, i);
		return getSentenceBoundaries(j >= 0 ? j : -j - 2);
	}

	private int[] getSentenceBoundaries(int j) {
		return new int[] { from[j], to[j] };
	}
}