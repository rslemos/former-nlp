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
package br.eti.rslemos.nlp.tagger.gate;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.FeatureMap;
import gate.util.InvalidOffsetException;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.SentenceIndexOutOfBoundsException;
import br.eti.rslemos.tagger.Token;

public class LowMemoryFootprintDocument extends AbstractList<Sentence> {

	private final int idxWORD;

	private final String[] featureNames;
	private final Object[][] features;
	
	private final int[] sentencesStart;
	private final int[] sentencesEnd;

	public LowMemoryFootprintDocument(Document doc, String annotationSetName, String... featureNames) {
		this(doc, doc.getAnnotations(annotationSetName), featureNames);
	}

	public LowMemoryFootprintDocument(Document doc, AnnotationSet annotations, String... featureNames) {
		this.featureNames = new String[featureNames.length + 1];
		System.arraycopy(featureNames, 0, this.featureNames, 0, featureNames.length);
		for (int i = 0; i < featureNames.length; i++) {
			this.featureNames[i] = this.featureNames[i].intern();
		}
		
		this.featureNames[featureNames.length] = Token.WORD;
		Arrays.sort(this.featureNames);
		
		idxWORD = Arrays.binarySearch(this.featureNames, Token.WORD);
		
		// build token matrix
		AnnotationSet tokenAnns = annotations.get("token");
		Iterator<Annotation> tokenAnnsIt = tokenAnns.iterator();
		
		features = new Object[tokenAnns.size()][this.featureNames.length];
		for (int i = 0; i < tokenAnns.size(); i++) {
			Annotation tokenAnn = tokenAnnsIt.next();

			try {
				features[i][idxWORD] = doc.getContent().getContent(tokenAnn.getStartNode().getOffset(), tokenAnn.getEndNode().getOffset()).toString();
			} catch (InvalidOffsetException e) {
				throw new RuntimeException(e);
			}
			
			FeatureMap tokenFeatureMap = tokenAnn.getFeatures();
			for (int j = 0; j < this.featureNames.length; j++) {
				if (j != idxWORD)
					features[i][j] = internalize(tokenFeatureMap.get(this.featureNames[j]));
			}
		}
		
		// build sentences as token offsets
		AnnotationSet sentenceAnns = annotations.get("sentence");
		Iterator<Annotation> sentenceAnnsIt = sentenceAnns.iterator();
		sentencesStart = new int[sentenceAnns.size()];
		sentencesEnd = new int[sentenceAnns.size()];
		
		for (int i = 0; i < sentenceAnns.size(); i++) {
			Annotation sentenceAnn = sentenceAnnsIt.next();
			long startOffset = sentenceAnn.getStartNode().getOffset();
			long endOffset = sentenceAnn.getEndNode().getOffset();
			
			tokenAnnsIt = tokenAnns.iterator();
			
			while (tokenAnnsIt.hasNext()) {
				Annotation tokenAnn = tokenAnnsIt.next();
				if (tokenAnn.getStartNode().getOffset() >= startOffset)
					break;
				
				sentencesStart[i]++;
			}
			
			sentencesEnd[i] = sentencesStart[i];
			while (tokenAnnsIt.hasNext()) {
				Annotation tokenAnn = tokenAnnsIt.next();
				if (tokenAnn.getEndNode().getOffset() > endOffset)
					break;
				
				sentencesEnd[i]++;
			}
			
			sentencesEnd[i]++;
		}
		
	}

	@Override
	public Sentence get(int index) {
		return new LowMemoryFootprintSentence(index);
	}

	@Override
	public int size() {
		return sentencesStart.length;
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

	private final class LowMemoryFootprintSentence implements Sentence {
		private final int index;

		private LowMemoryFootprintSentence(int index) {
			this.index = index;
		}

		@Override
		public int size() {
			return sentencesEnd[index] - sentencesStart[index];
		}

		@Override
		public Token get(int i) throws SentenceIndexOutOfBoundsException {
			return new LowMemoryFootprintToken(index, i);
		}

		@Override
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

		@Override
		public Object getFeature(String name) {
			return getFeatures().get(name);
		}

		@Override
		public Map<String, Object> getFeatures() {
			return new FeatureMap();
		}

		@Override
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

			@Override
			public boolean hasNext() {
				return j < featureNames.length;
			}

			@Override
			public Entry<String, Object> next() {
				try {
					return new SimpleEntry<String, Object>(featureNames[j], features[i + sentencesStart[index]][j]);
				} finally {
					j++;
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}

	}
}