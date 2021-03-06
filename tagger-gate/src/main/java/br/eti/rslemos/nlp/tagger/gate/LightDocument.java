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
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.SentenceIndexOutOfBoundsException;
import br.eti.rslemos.tagger.SimpleImmutableEntry;
import br.eti.rslemos.tagger.Token;

public final class LightDocument extends AbstractList<Sentence> {

	private final Document doc;
	private final AnnotationSet annAll;

	LightDocument(Document doc, String annotationSetName) {
		this(doc, doc.getAnnotations(annotationSetName));
	}

	private LightDocument(Document doc, AnnotationSet annAll) {
		this.doc = doc;
		this.annAll = annAll;
	}

	private Annotation getNthAnnotation(AnnotationSet anns, int i0) {
		Annotation[] allAnns = anns.toArray(new Annotation[anns.size()]);
		Arrays.sort(allAnns);
		
		return allAnns[i0];
	}

	@Override
	public Sentence get(int index) {
		return new LightSentence(this, getNthAnnotation(annAll.get("sentence"), index));
	}

	@Override
	public int size() {
		return annAll.get("sentence").size();
	}


	private final static class LightSentence extends AbstractList<Token> implements Sentence {
		private final Annotation annSentence;
		private final LightDocument lightDoc;

		private LightSentence(LightDocument lightDocument, Annotation annSentence) {
			this.lightDoc = lightDocument;
			this.annSentence = annSentence;
		}

		private AnnotationSet getAnnAllTokens() {
			return lightDoc.annAll.getContained(annSentence.getStartNode().getOffset(), annSentence.getEndNode().getOffset()).get("token");
		}

		@Override
		public Token get(int i) throws SentenceIndexOutOfBoundsException {
			Annotation nthAnnotation = lightDoc.getNthAnnotation(getAnnAllTokens(), i);
			return new LightToken(lightDoc.doc, nthAnnotation, nthAnnotation.getFeatures());
		}

		@Override
		public int size() {
			return getAnnAllTokens().size();
		}
	}
	
	private static final class LightToken extends AbstractMap<String, Object> implements Token {
		private final Document doc;
		private final Annotation annToken;
		private final FeatureMap features;

		private LightToken(Document doc, Annotation annToken, FeatureMap features) {
			this.doc = doc;
			this.annToken = annToken;
			this.features = features;
		}

		@Override
		public Set<Entry<String, Object>> entrySet() {
			return new TokenFeatureSet(features, doc, annToken);
		}
	}

	private static final class TokenFeatureSet extends AbstractSet<Entry<String, Object>> {
		private final FeatureMap features;
		private final Document doc;
		private final Annotation annToken;

		private TokenFeatureSet(FeatureMap features, Document doc, Annotation annToken) {
			this.features = features;
			this.doc = doc;
			this.annToken = annToken;
		}

		@Override
		public Iterator<Entry<String, Object>> iterator() {
			return new TokenFeatureIterator(features != null ? features.entrySet().iterator() : null, annToken, doc);
		}

		@Override
		public int size() {
			return (features != null ? features.size() : 0) + 1;
		}
	}

	private static final class TokenFeatureIterator implements Iterator<Map.Entry<String, Object>> {
		private final Iterator<Entry<Object, Object>> iterator;
		private final Annotation annToken;
		private final Document doc;
		boolean first = true;

		private TokenFeatureIterator(Iterator<Entry<Object, Object>> iterator, Annotation annToken, Document doc) {
			this.iterator = iterator;
			this.annToken = annToken;
			this.doc = doc;
		}

		public boolean hasNext() {
			if (first)
				return true;
			else
				return iterator != null ? iterator.hasNext() : false;
		}

		public Entry<String, Object> next() {
			if (first) {
				first = false;
				try {
					return new SimpleImmutableEntry<String, Object>(Token.WORD, 
							doc.getContent().getContent(annToken.getStartNode().getOffset(), annToken.getEndNode().getOffset()).toString()
						);
				} catch (InvalidOffsetException e) {
					throw new RuntimeException(e);
				}
			} else if (iterator != null) {
				Entry<Object, Object> entry = iterator.next();
				return new SimpleImmutableEntry<String, Object>(String.valueOf(entry.getKey()), entry.getValue());
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
