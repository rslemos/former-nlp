/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2013  Rodrigo Lemos
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
package br.eti.rslemos.brill;

import br.eti.rslemos.tagger.Corpus;
import br.eti.rslemos.tagger.DefaultCorpus;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public class CorpusUtils {
	public static Corpus retainFeatures(Corpus in, String feature) {
		DefaultCorpus corpus = new DefaultCorpus();
		
		for (Sentence sentence : in) {
			corpus.add(retainFeatures(sentence, feature));
		}
		
		return corpus;
	}

	private static Sentence retainFeatures(Sentence in, String feature) {
		DefaultSentence sentence = new DefaultSentence();
		
		for (Token token : in) {
			sentence.add(retainFeatures(token, feature));
		}
		
		return sentence;
	}

	private static Token retainFeatures(Token in, String feature) {
		DefaultToken token = new DefaultToken();
		
		if (in.containsKey(feature))
			token.put(feature, in.get(feature));
		
		return token;
	}

	public static Corpus corpus(Sentence... sentences) {
		DefaultCorpus corpus = new DefaultCorpus();
		
		for (Sentence sentence : sentences) {
			corpus.add(sentence);
		}
		
		return corpus;
	}

	public static Sentence sentence(String features, String... words) {
		String[] features0 = features.split("/");
		
		DefaultSentence sentence = new DefaultSentence();
		
		for (String word : words) {
			String[] values = word.split("/");
			DefaultToken token = new DefaultToken();
			for (int i = 0; i < values.length; i++) {
				token.put(features0[i], values[i]);
			}
			sentence.add(token);
		}
		
		return sentence;
	}
}