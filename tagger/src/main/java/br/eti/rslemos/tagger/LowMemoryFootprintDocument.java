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
package br.eti.rslemos.tagger;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class LowMemoryFootprintDocument extends AbstractList<Sentence> {

	private final String[] featureNames;
	private final Object[][] features;
	
	private final int[] sentencesEnd;

	public LowMemoryFootprintDocument(List<Sentence> doc, String... featureNames) {
		// build set of feature names
		// (add Token.WORD, avoid duplicates, sort and intern()) 
		TreeSet<String> featureNameSet = new TreeSet<String>(Arrays.asList(featureNames));
		featureNameSet.add(Token.WORD);
		this.featureNames = featureNameSet.toArray(new String[featureNameSet.size()]);
		for (int i = 0; i < this.featureNames.length; i++) {
			this.featureNames[i] = this.featureNames[i].intern();
		}
		
		final int idxWORD = Arrays.binarySearch(this.featureNames, Token.WORD);
		
		// build sentences as token offsets (and compute feature matrix size)
		sentencesEnd = new int[doc.size()];
		int i = 0;
		Iterator<Sentence> sentenceIt = doc.iterator();
		while (sentenceIt.hasNext()) {
			Sentence sentence = sentenceIt.next();
			sentencesEnd[i] = getSentenceStart(i) + sentence.size();
			i++;
		}
		
		// build token matrix
		features = new Object[sentencesEnd[doc.size()-1]][this.featureNames.length];
		int j = 0;
		for (Sentence sentence : doc) {
			for (Token token : sentence) {
				Map<String, Object> featureMap = token.getFeatures();

				for (int k = 0; k < this.featureNames.length; k++) {
					features[j][k] = featureMap.get(this.featureNames[k]);
					if (k != idxWORD)
						features[j][k] = internalize(features[j][k]);
				}
				
				j++;
			}
		}
		
	}

	@Override
	public Sentence get(int index) {
		return new LowMemoryFootprintSentence(index);
	}

	@Override
	public int size() {
		return sentencesEnd.length;
	}

	private static Object internalize(Object o) {
		if (o instanceof String)
			return ((String)o).intern();

		if (o instanceof Boolean)
			return Boolean.valueOf((Boolean)o);

		if (o instanceof Integer)
			return Integer.valueOf((Integer)o);

		if (o instanceof Long)
			return Long.valueOf((Long)o);
		
		if (o instanceof Character)
			return Character.valueOf((Character)o);
		
		if (o instanceof Short)
			return Short.valueOf((Short)o);
		
		if (o instanceof Byte)
			return Byte.valueOf((Byte)o);
		
		return o;
	}

	private int getSentenceStart(int index) {
		return index > 0 ? sentencesEnd[index-1] : 0;
	}

	private final class LowMemoryFootprintSentence implements Sentence {
		private final int index;

		private LowMemoryFootprintSentence(int index) {
			this.index = index;
		}

		public int size() {
			return sentencesEnd[index] - getSentenceStart(index);
		}

		public Token get(int i) throws SentenceIndexOutOfBoundsException {
			return new LowMemoryFootprintToken(index, i);
		}

		public Iterator<Token> iterator() {
			throw new UnsupportedOperationException();
		}
	}
	
	private final class LowMemoryFootprintToken implements Token {
		private final int i;
		private final int index;

		private LowMemoryFootprintToken(int index, int i) {
			this.index = index;
			this.i = i;
		}

		public Object getFeature(String name) {
			return getFeatures().get(name);
		}

		public Map<String, Object> getFeatures() {
			return new FeatureMap();
		}

		public Token setFeature(String name, Object value) {
			throw new UnsupportedOperationException();
		}

		private final class FeatureMap extends AbstractMap<String, Object> {
			@Override
			public Set<Entry<String, Object>> entrySet() {
				return new FeatureSet();
			}
		}
		
		private final class FeatureSet extends AbstractSet<Entry<String, Object>> {
			@Override
			public Iterator<Entry<String, Object>> iterator() {
				return new FeatureSetIterator();
			}

			@Override
			public int size() {
				return featureNames.length;
			}
		}

		private final class FeatureSetIterator implements Iterator<Entry<String, Object>> {
			int j = 0;

			public boolean hasNext() {
				return j < featureNames.length;
			}

			public Entry<String, Object> next() {
				try {
					return new SimpleEntry<String, Object>(featureNames[j], features[i + getSentenceStart(index)][j]);
				} finally {
					j++;
				}
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		}

	}
}