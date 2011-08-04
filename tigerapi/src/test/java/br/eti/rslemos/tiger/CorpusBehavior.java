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
package br.eti.rslemos.tiger;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("unchecked")
public abstract class CorpusBehavior {

	public abstract Corpus getStraightCorpus()
	throws UnsupportedEncodingException, TigerException;

	@Test
	public void shouldReadId() throws Throwable {
		Corpus corpus = getStraightCorpus();

		TigerInputMother.testStraightCorpusAttributes(corpus);
	}

	@Test
	public void shouldReadMetadata() throws Throwable {
		Corpus corpus = getStraightCorpus();
		Meta meta = corpus.getHead().getMeta();

		TigerInputMother.testStraightCorpusMeta(meta);
	}

	@Test
	public void shouldHaveFeatures() throws Throwable {
		Corpus corpus = getStraightCorpus();
		List<Feature> features = (List<Feature>) corpus.getHead().getAnnotation().getFeatures();

		TigerInputMother.testStraightCorpusFeaturesAvailability(features);
	}

	@Test
	public void shouldHaveValuedFeature() throws Throwable {
		Corpus corpus = getStraightCorpus();
		List<Feature> features = (List<Feature>) corpus.getHead().getAnnotation().getFeatures();

		TigerInputMother.testStraightCorpusValuedFeature(features);
	}

	@Test
	public void shouldFullyReadFeatures() throws Throwable {
		Corpus corpus = getStraightCorpus();
		List<Feature> features = (List<Feature>) corpus.getHead().getAnnotation().getFeatures();

		TigerInputMother.testStraightCorpusFeatures(features);
	}

}
