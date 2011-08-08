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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LookupTokenTagger extends AbstractTokenTagger implements Serializable {

	private static final long serialVersionUID = 6023313611881081572L;

	private Map<String, Object> lexicon;

	public LookupTokenTagger() {
		this(new HashMap<String, Object>());
	}

	public LookupTokenTagger(Map<String, Object> lexicon) {
		this.lexicon = lexicon;
	}

	@Override
	public void tag(Token token) {
		String word = (String) token.get(Token.WORD);
		if (lexicon.containsKey(word))
			token.put(Token.POS, lexicon.get(word));
	}

	public Map<String, Object> getLexicon() {
		return lexicon;
	}

	public void setLexicon(Map<String, Object> lexicon) {
		this.lexicon = lexicon;
	}
}
