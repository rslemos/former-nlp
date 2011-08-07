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

import static br.eti.rslemos.tagger.DocumentUnitTestHelper.buildSentence;
import static br.eti.rslemos.tagger.DocumentUnitTestHelper.buildText;
import static br.eti.rslemos.tagger.DocumentUnitTestHelper.buildToken;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.common.collect.testing.Helpers;
import com.google.common.collect.testing.MapInterfaceTest;
import com.google.common.collect.testing.MapTestSuiteBuilder;
import com.google.common.collect.testing.SampleElements;
import com.google.common.collect.testing.TestMapGenerator;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.MapFeature;

public class LowMemoryFootprintDocument$FeatureMapUnitTest extends MapInterfaceTest<String, Object> {

	public static Test suite() {
		TestSuite suite = new TestSuite(LowMemoryFootprintDocument$FeatureMapUnitTest.class);
		
		suite.addTest(MapTestSuiteBuilder
				.using(
						new TestMapGenerator<String, Object>() {

							public SampleElements<Entry<String, Object>> samples() {
								return new SampleElements<Entry<String, Object>>(
										Helpers.mapEntry(Token.POS , (Object)"POS"),
										Helpers.mapEntry(Token.WORD, (Object)"WORD"),
										Helpers.mapEntry("featureX", (Object)"X"),
										Helpers.mapEntry("featureY", (Object)"Y"),
										Helpers.mapEntry("featureZ", (Object)"Z")
								);
							}

							@SuppressWarnings("unchecked")
							public Map<String, Object> create(Object... elements) {
								Entry<String, Object>[] elements0 = new Entry[elements.length];
								for (int i = 0; i < elements.length; i++) {
									elements0[i] = (Entry<String, Object>) elements[i];
								}
								return create(elements0);
							}

							private Map<String, Object> create(Entry<String, Object>... elements) {
								Token token = buildToken(null, null);
								for (Entry<String, Object> element : elements) {
									token.setFeature(element.getKey(), element.getValue());
								}

								List<Sentence> text = buildText(buildSentence(token));
								LowMemoryFootprintDocument doc = new LowMemoryFootprintDocument(text, token.getFeatures().keySet().toArray(new String[0]));

								Map<String, Object> map = doc.get(0).get(0).getFeatures();
								return map;
							}

							@SuppressWarnings("unchecked")
							public Entry<String, Object>[] createArray(int length) {
								return new Entry[length];
							}

							public Iterable<Entry<String, Object>> order(List<Entry<String, Object>> insertionOrder) {
								Collections.sort(insertionOrder, new Comparator<Entry<String, Object>>() {
									public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
										return o1.getKey().compareTo(o2.getKey());
									}
								});
								return insertionOrder;
							}

							public String[] createKeyArray(int length) {
								return new String[length];
							}

							public Object[] createValueArray(int length) {
								return new Object[length];
							}
						}
					)
				.withFeatures(
						MapFeature.ALLOWS_NULL_VALUES,
						MapFeature.RESTRICTS_KEYS,
						MapFeature.SUPPORTS_PUT,
						MapFeature.SUPPORTS_PUT_ALL,
						CollectionFeature.KNOWN_ORDER,
						CollectionSize.SEVERAL
					)
				.named(LowMemoryFootprintDocument$FeatureMapUnitTest.class.getName())
				.createTestSuite()
			);

		return suite;
	}

	public LowMemoryFootprintDocument$FeatureMapUnitTest() {
		super(false, true, true, false, false, false);
	}

	@Override
	protected Map<String, Object> makeEmptyMap() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Map<String, Object> makePopulatedMap() throws UnsupportedOperationException {
		List<Sentence> text = buildText(
				buildSentence(
						buildToken("foo", "bar").setFeature("featureX", 10)
					)
			);
		
		LowMemoryFootprintDocument doc = new LowMemoryFootprintDocument(text, Token.WORD, Token.POS, "featureX", "featureY");
		
		return doc.get(0).get(0).getFeatures();
	}

	@Override
	protected String getKeyNotInPopulatedMap() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object getValueNotInPopulatedMap() throws UnsupportedOperationException {
		return 20;
	}
	
}