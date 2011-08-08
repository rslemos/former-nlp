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

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.LowMemoryFootprintDocument;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public class LowMemoryFootprintDocumentUnitTest extends DocumentUnitTestHelper {

	@Override
	protected List<Sentence> createListOfSentences(Entry<String, Entry<String, String>[]>[][] doc_def) {
		return new LowMemoryFootprintDocument(createDocument(doc_def), extractFeatureNames(doc_def));
	}
	
	private static List<Sentence> createDocument(Entry<String, Entry<String, String>[]>[][] doc_def) {
		String[] sentencesText = makeSentencesText(doc_def);
		
		List<Sentence> doc = new ArrayList<Sentence>(doc_def.length);
		String fullText = makeFullText(sentencesText);
		
		int start = 0;
		for (int i = 0; i < doc_def.length; i++) {
			Entry<String, Entry<String, String>[]>[] token_defs = doc_def[i];
			List<Token> tokens = new ArrayList<Token>(token_defs.length);
				
			for (Entry<String, Entry<String, String>[]> token_def : token_defs) {
				String word = token_def.getKey();
				DefaultToken token = new DefaultToken(fullText.substring(start, start + word.length()));
				tokens.add(token);
				
				start += word.length() + 1;
				
				Entry<String, String>[] feature_defs = token_def.getValue();
				if (feature_defs != null) {
					for (Entry<String, String> feature_def : feature_defs) {
						token.put(feature_def.getKey(), feature_def.getValue());
					}
				}
			}
			doc.add(new DefaultSentence(tokens));
		}
		
		return doc;
	}
}
