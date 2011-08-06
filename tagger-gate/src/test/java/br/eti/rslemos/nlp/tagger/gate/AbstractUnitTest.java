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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;

import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

@RunWith(Theories.class)
public abstract class AbstractUnitTest implements DocumentDataPoints {
	
	private Document doc;
	private List<Sentence> sentences;

	@Theory
	public void testConversion(Entry<String, Entry<String, String>[]>[][] doc_def) throws Exception {
		createDocument(doc_def);
		
		sentences = createListOfSentences(doc, "annotationSet");

		checkSentences(doc_def);
	}

	protected abstract List<Sentence> createListOfSentences(Document doc, String annotationSetName);

	private void createDocument(Entry<String, Entry<String, String>[]>[][] doc_def) throws InvalidOffsetException {
		doc = new DocumentImpl();
		
		String[] sentenceText = new String[doc_def.length];
		for (int i = 0; i < doc_def.length; i++) {
			StringBuilder builder = new StringBuilder();
			
			for (Entry<String, Entry<String, String>[]> sentence : doc_def[i]) {
				builder.append(sentence.getKey());
				builder.append(' ');
			}
			
			sentenceText[i] = builder.substring(0, builder.length() - 1);
		}
		
		doc.setContent(new DocumentContentImpl(makeSentenceText(sentenceText)));
		AnnotationSet annotations = doc.getAnnotations("annotationSet");
		
		long start = 0;
		for (int i = 0; i < doc_def.length; i++) {
			Entry<String, Entry<String, String>[]>[] tokens = doc_def[i];
			
			annotations.add(start, start + (long) sentenceText[i].length(), "sentence", null);
			
			for (Entry<String, Entry<String, String>[]> token : tokens) {
				String word = token.getKey();
				
				int id = annotations.add(start, start + (long) word.length(), "token", null);
				start += word.length() + 1;
				
				Annotation ann = annotations.get(id);
				ann.setFeatures(new SimpleFeatureMapImpl());
				
				for (Entry<String, String> feature : token.getValue()) {
					ann.getFeatures().put(feature.getKey(), feature.getValue());
				}
			}
		}
	}

	private void checkSentences(Entry<String, Entry<String, String>[]>[][] doc_def) {
		assertThat(sentences, is(not(nullValue(List.class))));
		assertThat(sentences.size(), is(equalTo(doc_def.length)));
		
		for (int i = 0; i < doc_def.length; i++) {
			checkSentence(doc_def, i);
		}
	}

	private void checkSentence(Entry<String, Entry<String, String>[]>[][] doc_def, int sentence_idx) {
		Entry<String, Entry<String, String>[]>[] sentence_def = doc_def[sentence_idx];

		Sentence sentence = sentences.get(sentence_idx);
		
		assertThat(sentence, is(not(nullValue(Sentence.class))));
		assertThat(sentence.size(), is(equalTo(sentence_def.length)));
		
		for (int i = 0; i < sentence_def.length; i++) {
			checkToken(doc_def, sentence_idx, i);
		}
	}

	private void checkToken(Entry<String, Entry<String, String>[]>[][] doc_def, int sentence_idx, int token_idx) {
		Entry<String, Entry<String, String>[]> token_def = doc_def[sentence_idx][token_idx];
		
		Sentence sentence = sentences.get(sentence_idx);
		Token token = sentence.get(token_idx);
		
		assertThat(token, is(not(nullValue(Token.class))));
		assertThat(token.getFeatures().size(), is(equalTo(token_def.getValue().length + 1)));
		assertThat(token.getFeature(Token.WORD), is(equalTo((Object)token_def.getKey())));
		assertThat(token.getFeatures().get(Token.WORD), is(equalTo((Object)token_def.getKey())));
		
		for (Entry<String, String> feature_def : token_def.getValue()) {
			assertThat(token.getFeature(feature_def.getKey()), is(equalTo((Object)feature_def.getValue())));
			assertThat(token.getFeatures().get(feature_def.getKey()), is(equalTo((Object)feature_def.getValue())));
		}
	}

	private String makeSentenceText(String... words) {
		StringBuilder result = new StringBuilder();
		
		for (String word : words) {
			result.append(word);
			result.append(' ');
		}
		
		return result.substring(0, result.length() - 1);
	}
	
	// used to construct datapoints
	public static <K,V> Entry<K, V> makeEntry(K key, V value) {
		return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
	}

	public static <T> T[] makeTypedList(Class<T> clazz, T... v) {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(clazz, v.length);
		System.arraycopy(v, 0, result, 0, v.length);
		
		return result;
	}
	
	public static <T> T[] makeList(T... v) {
		return v;
	}

}
