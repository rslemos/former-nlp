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
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;

import java.util.List;
import java.util.Map.Entry;

import br.eti.rslemos.tagger.Sentence;

public abstract class AbstractUnitTest extends DocumentUnitTestHelper {
	
	@Override
	protected List<Sentence> createListOfSentences(Entry<String, Entry<String, String>[]>[][] doc_def) {
		Document doc = createDocument(doc_def);
		reportFootprint("doc", doc);

		return createListOfSentences(doc, "annotationSet");
	}
	
	protected abstract List<Sentence> createListOfSentences(Document doc, String annotationSetName);

	private Document createDocument(Entry<String, Entry<String, String>[]>[][] doc_def) {
		String[] sentencesText = makeSentencesText(doc_def);
		
		Document doc = new DocumentImpl();
		doc.setContent(new DocumentContentImpl(makeFullText(sentencesText)));
		AnnotationSet annotations = doc.getAnnotations("annotationSet");
		
		long start = 0;
		for (int i = 0; i < doc_def.length; i++) {
			Entry<String, Entry<String, String>[]>[] tokens = doc_def[i];
			
			try {
				annotations.add(start, start + (long) sentencesText[i].length(), "sentence", null);
				
				for (Entry<String, Entry<String, String>[]> token : tokens) {
					String word = token.getKey();
					
					int id = annotations.add(start, start + (long) word.length(), "token", null);
					start += word.length() + 1;
					
					Entry<String, String>[] feature_defs = token.getValue();
					if (feature_defs != null) {
						Annotation ann = annotations.get(id);
						ann.setFeatures(new SimpleFeatureMapImpl());
						ann.getFeatures().putAll(wrapMap(feature_defs));
					}
				}
			} catch (InvalidOffsetException e) {
				throw new RuntimeException(e);
			}
		}
		
		return doc;
	}

}
