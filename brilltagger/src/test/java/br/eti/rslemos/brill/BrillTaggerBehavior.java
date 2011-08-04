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
package br.eti.rslemos.brill;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import org.junit.Test;
import org.mockito.InOrder;

import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerBehavior {

	@Test
	public void shouldWorkOnEmptyInput() {
		BrillTagger tagger = new BrillTagger();
		
		tagger.tag(newDefaultSentence());
	}

	@Test
	public void shouldInvokeBaseTaggerAndRuleAndObjectToken() {
		Token token = mock(Token.class);
		when(token.getFeature(Token.WORD)).thenReturn("foo");
		when(token.getFeature(Token.POS)).thenReturn("bar");

		Rule rule = new RuleAdapter(null, "foobar") {
			@Override
			public boolean matches(Context context) {
				Token token = context.getToken(0);
				assertEquals(token.getFeature(Token.WORD), "foo");
				assertEquals(token.getFeature(Token.POS), "bar");
				
				return true;
			}
		};
		
		BrillTagger tagger = new BrillTagger(Arrays.asList(rule));
		
		tagger.tag(newDefaultSentence(token));

		verify(token, times(1)).setFeature(Token.POS, "foobar");
	}

	@Test
	public void shouldInvokeRulesInOrder() {
		Token token = mock(Token.class);

		Rule rule1 = mock(Rule.class);
		Rule rule2 = mock(Rule.class);
		
		BrillTagger tagger = new BrillTagger(Arrays.asList(rule1, rule2));
		
		tagger.tag(newDefaultSentence(token));

		InOrder inOrder = inOrder(rule1, rule2);
		inOrder.verify(rule1, times(1)).matches(anyContext());
		inOrder.verify(rule2, times(1)).matches(anyContext());
	}

	private Context anyContext() {
		return (Context) anyObject();
	}

	@Test
	public void shouldIsolateRuleEffects() {
		final Token token1 = mock(Token.class);
		final Token token2 = mock(Token.class);

		Rule rule = new RuleAdapter(null, "foobar") {
			@Override
			public boolean matches(Context context) {
				verify(token2, never()).setFeature(same(Token.POS), anyObject());
				verify(token1, never()).setFeature(same(Token.POS), anyObject());
				
				return true;
			}
		};

		BrillTagger tagger = new BrillTagger(Arrays.asList(rule));
		
		tagger.tag(newDefaultSentence(token1, token2));

		verify(token1, times(1)).setFeature(Token.POS, "foobar");
		verify(token2, times(1)).setFeature(Token.POS, "foobar");
	}

	private static class RuleAdapter extends AbstractRule {
		public RuleAdapter(Object from, Object to) {
			super(from, to);
		}

		@Override
		public boolean matches(Context context) {
			return false;
		}

		@Override
		public boolean testsTag(Object tag) {
			return false;
		}

		@Override
		public void writeRule(Writer out) throws IOException {
		}
	}

	private DefaultSentence newDefaultSentence(Token... tokens) {
		return new DefaultSentence(Arrays.asList(tokens));
	}
	
}
