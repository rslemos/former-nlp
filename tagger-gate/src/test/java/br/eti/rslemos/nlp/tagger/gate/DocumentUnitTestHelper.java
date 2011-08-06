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

import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import objectexplorer.ObjectGraphMeasurer;
import objectexplorer.ObjectGraphMeasurer.Footprint;

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

import com.google.common.collect.ImmutableMultiset;

@RunWith(Theories.class)
public abstract class DocumentUnitTestHelper implements DocumentDataPoints {

	protected abstract List<Sentence> createListOfSentences(Entry<String, Entry<String, String>[]>[][] doc_def);

	private List<Sentence> sentences;

	@Theory
	public void testConversion(Entry<String, Entry<String, String>[]>[][] doc_def) throws Exception {
		System.out.println("==========");
		
		sentences = createListOfSentences(doc_def);
		
		checkSentences(doc_def);
	}

	protected static String[] extractFeatureNames(Entry<String, Entry<String, String>[]>[][] doc_def) {
		HashSet<String> featureNames = new HashSet<String>();
		
		for (Entry<String, Entry<String, String>[]>[] sentence_def : doc_def) {
			for (Entry<String, Entry<String, String>[]> token_def : sentence_def) {
				Entry<String, String>[] features = token_def.getValue();
				if (features != null) {
					for (Entry<String, String> feature : features) {
						featureNames.add(feature.getKey());
					}
				}
			}
		}
		
		return featureNames.toArray(new String[featureNames.size()]);
	}

	private void checkSentences(Entry<String, Entry<String, String>[]>[][] doc_def) {
		reportFootprint("sentences", sentences);
		
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
		
		reportFootprint("sentence_" + sentence_idx , sentence);
	
		for (int i = 0; i < sentence_def.length; i++) {
			checkToken(doc_def, sentence_idx, i);
		}
	}

	private void checkToken(Entry<String, Entry<String, String>[]>[][] doc_def, int sentence_idx, int token_idx) {
		Entry<String, Entry<String, String>[]> token_def = doc_def[sentence_idx][token_idx];
		Entry<String, String>[] feature_defs = token_def.getValue();
		
		Sentence sentence = sentences.get(sentence_idx);
		Token token = sentence.get(token_idx);
		
		assertThat(token, is(not(nullValue(Token.class))));
		
		assertThat(token.getFeature(Token.WORD), is(equalTo((Object)token_def.getKey())));
		assertThat(token.getFeatures().get(Token.WORD), is(equalTo((Object)token_def.getKey())));
	
		if (feature_defs != null) {
			assertThat(token.getFeatures().size(), is(equalTo(feature_defs.length + 1)));
			for (Entry<String, String> feature_def : feature_defs) {
				assertThat(token.getFeature(feature_def.getKey()), is(equalTo((Object)feature_def.getValue())));
				assertThat(token.getFeatures().get(feature_def.getKey()), is(equalTo((Object)feature_def.getValue())));
			}
		} else {
			assertThat(token.getFeatures().size(), is(equalTo(1)));
		}
		
		reportFootprint("token_" + sentence_idx + "_" + token_idx,   token);
	}

	protected static void reportFootprint(String name, Object o) {
		Footprint footprint = ObjectGraphMeasurer.measure(o);
		System.out.printf("%20s: %10d (%s)\n", name, computeFootprint(footprint), footprint);
	}

	private static int computeFootprint(Footprint fp) {
		final int REFERENCE_SIZE = 64 / 8; //memory pointer
		final int OBJECT_SIZE = 1*REFERENCE_SIZE; //assuming 1 pointer per object allocation
		final int BOOLEAN_SIZE = 2;
		final int BYTE_SIZE = 1;
		final int CHAR_SIZE = 2;
		final int SHORT_SIZE = 2;
		final int INT_SIZE = 4;
		final int LONG_SIZE = 8;
		final int FLOAT_SIZE = 4;
		final int DOUBLE_SIZE = 8;
		
		ImmutableMultiset<Class<?>> p = fp.getPrimitives();
		
		return 
			fp.getReferences()     * REFERENCE_SIZE +
			fp.getObjects()        * OBJECT_SIZE    +
			p.count(boolean.class) * BOOLEAN_SIZE   +
			p.count(byte.class)    * BYTE_SIZE      +
			p.count(char.class)    * CHAR_SIZE      +
			p.count(short.class)   * SHORT_SIZE     +
			p.count(int.class)     * INT_SIZE       +
			p.count(long.class)    * LONG_SIZE      +
			p.count(float.class)   * FLOAT_SIZE     +
			p.count(double.class)  * DOUBLE_SIZE;
	}

	public static <K, V> Entry<K, V> makeEntry(K key, V value) {
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

	public static <K, V> Map<K, V> wrapMap(Entry<K, V>... defs) {
		Map<K,V> map = new HashMap<K, V>();
		
		for (Entry<K, V> def : defs) {
			map.put(def.getKey(), def.getValue());
		}
	
		return map;
	}

	protected static String[] makeSentencesText(Entry<String, Entry<String, String>[]>[][] doc_def) {
		String[] sentenceText = new String[doc_def.length];
		for (int i = 0; i < doc_def.length; i++) {
			StringBuilder builder = new StringBuilder();
			
			for (Entry<String, Entry<String, String>[]> sentence : doc_def[i]) {
				builder.append(sentence.getKey());
				builder.append(' ');
			}
			
			sentenceText[i] = builder.substring(0, builder.length() - 1);
		}
		return sentenceText;
	}

	protected static String makeFullText(String... sentences) {
		StringBuilder result = new StringBuilder();
		
		for (String sentence : sentences) {
			result.append(sentence);
			result.append(' ');
		}
		
		return result.substring(0, result.length() - 1);
	}

}
