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

import static br.eti.rslemos.nlp.tagger.gate.AbstractUnitTest.makeEntry;
import static br.eti.rslemos.nlp.tagger.gate.AbstractUnitTest.makeList;
import static br.eti.rslemos.nlp.tagger.gate.AbstractUnitTest.makeTypedList;

import java.util.Map.Entry;

import org.junit.experimental.theories.DataPoint;

@SuppressWarnings("unchecked")
public interface DocumentDataPoints {
	@DataPoint public static final Entry<String, Entry<String, String>[]>[][] oneSentenceWithOneToken1 = makeTypedList(
			Entry[].class,
			makeList(
					makeEntry("word", makeTypedList(Entry.class))
				)
		);
	
	@DataPoint public static final Entry<String, Entry<String, String>[]>[][] oneSentenceWithOneToken2 = makeTypedList(
			Entry[].class,
			makeList(
					makeEntry("other", makeTypedList(Entry.class))
				)
		);

	@DataPoint public static final Entry<String, Entry<String, String>[]>[][] oneSentenceWithTwoTokens = makeTypedList(
			Entry[].class,
			makeList(
					makeEntry("other", makeTypedList(Entry.class)),
					makeEntry("word" , makeTypedList(Entry.class))
				)
		);

	@DataPoint public static final Entry<String, Entry<String, String>[]>[][] oneSentenceWithTwoTokensEachWithOneFeature = makeTypedList(
			Entry[].class,
			makeList(
					makeEntry("other", makeList(makeEntry("feature", "value for other"))),
					makeEntry("word" , makeList(makeEntry("feature", "value for word" )))
				)
		);

	@DataPoint public static final Entry<String, Entry<String, String>[]>[][] twoSentencesEachWithTwoTokensEachWithOneFeature = makeList(
			makeList(
					makeEntry("other", makeList(makeEntry("feature", "value for other"))),
					makeEntry("word" , makeList(makeEntry("feature", "value for word" )))
				),
			makeList(
					makeEntry("hello", makeList(makeEntry("feature", "value for hello"))),
					makeEntry("world", makeList(makeEntry("feature", "value for world")))
				)
		);
}
