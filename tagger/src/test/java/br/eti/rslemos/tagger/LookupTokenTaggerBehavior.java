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

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;

import org.junit.Test;

public class LookupTokenTaggerBehavior {
	@Test
	public void shouldNotObjectToken() {
		Token token = mock(Token.class);
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Object)"bar"));
		tagger.tag(token);
		
		verify(token, never()).setFeature(same(Token.POS), anyObject());
	}

	@Test
	public void shouldObjectToken() {
		Token token = mock(Token.class);
		when(token.getFeature(Token.WORD)).thenReturn("foo");
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Object)"bar"));
		tagger.tag(token);
		
		verify(token).setFeature(Token.POS, "bar");
	}

	@Test
	public void shouldNullObjectToken() {
		Token token = mock(Token.class);
		when(token.getFeature(Token.WORD)).thenReturn("foo");
		
		LookupTokenTagger tagger = new LookupTokenTagger(Collections.singletonMap("foo", (Object)null));
		tagger.tag(token);
		
		verify(token).setFeature(Token.POS, null);
	}

	@Test
	public void shouldCorreclyObjectTokens() {
		Token token1 = mock(Token.class);
		when(token1.getFeature(Token.WORD)).thenReturn("foo");

		Token token2 = mock(Token.class);
		when(token2.getFeature(Token.WORD)).thenReturn("bar");
		
		HashMap<String, Object> lexicon = new HashMap<String, Object>();
		lexicon.put("foo", "tag-foo");
		lexicon.put("bar", "tag-bar");
		
		LookupTokenTagger tagger = new LookupTokenTagger(lexicon);

		tagger.tag(token1);
		verify(token1).setFeature(Token.POS, "tag-foo");

		tagger.tag(token2);
		verify(token2).setFeature(Token.POS, "tag-bar");
	}
}
