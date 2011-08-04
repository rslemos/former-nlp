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
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.mockito.InOrder;
import org.junit.Test;

public class CompositeTaggerBehavior {
	
	@Test
	public void shouldTransferTokenProperties() {
		Token token = mock(Token.class);
		when(token.getFeature(Token.WORD)).thenReturn("foo");
		when(token.getFeature(Token.POS)).thenReturn("bar");
		
		Tagger subTagger = new AbstractTokenTagger() {
			@Override
			public void tag(Token token) {
				assertEquals(token.getFeature(Token.WORD), "foo");
				assertEquals(token.getFeature(Token.POS), "bar");
			}
		};
		
		tagToken(buildTagger(subTagger), token);
	}

	@Test
	public void shouldNotObjectToken() {
		Token token = mock(Token.class);
		
		tagToken(this.buildTagger(), token);

		verify(token, never()).setFeature(same(Token.POS), anyObject());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndStillNotObjectToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = mock(Tagger.class);
		Tagger subTagger3 = mock(Tagger.class);
		
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		InOrder inOrder = inOrder(subTagger1, subTagger2, subTagger3);
		
		inOrder.verify(subTagger1).tag(anySentence());
		inOrder.verify(subTagger2).tag(anySentence());
		inOrder.verify(subTagger3).tag(anySentence());
		
		verify(token, never()).setFeature(same(Token.POS), anyObject());
	}

	@Test
	public void shouldInvokeAllSubTaggersInOrderAndObjectToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = new AbstractTokenTagger() {
			@Override
			public void tag(Token token) {
				token.setFeature(Token.POS, "foobar");
			}
		};
		Tagger subTagger3 = mock(Tagger.class);
		
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		verify(subTagger1).tag(anySentence());
		verify(subTagger3).tag(anySentence());
		
		verify(token).setFeature(Token.POS, "foobar");
	}

	@Test
	public void shouldInvokeAllSubTaggersAndIgnoreObjectOnJustObjectgedToken() {
		Token token = mock(Token.class);
		
		Tagger subTagger1 = mock(Tagger.class);
		Tagger subTagger2 = new AbstractTokenTagger() {
			@Override
			public void tag(Token token) {
				token.setFeature(Token.POS, "foobar");
			}
		};

		final boolean[] check = { false };
		Tagger subTagger3 = new AbstractTokenTagger() {
			@Override
			public void tag(Token token) {
				check[0] = true;
				token.setFeature(Token.POS, "foobar");
			}
		};
		tagToken(buildTagger(subTagger1, subTagger2, subTagger3), token);
		
		assertTrue(check[0]);
		
		verify(subTagger1).tag(anySentence());
		verify(token, times(1)).setFeature(Token.POS, "foobar");
	}

	private void tagToken(CompositeTagger tagger, Token token) {
		tagger.tag(new DefaultSentence(Collections.singletonList(token)));
	}

	private CompositeTagger buildTagger(Tagger... taggers) {
		return new CompositeTagger(taggers);
	}

	private static Sentence anySentence() {
		return anyObject();
	}
}
