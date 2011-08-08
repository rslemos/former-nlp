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

import static br.eti.rslemos.tagger.DocumentUnitTestHelper.buildSentence;
import static br.eti.rslemos.tagger.DocumentUnitTestHelper.buildText;
import static br.eti.rslemos.tagger.DocumentUnitTestHelper.buildToken;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import br.eti.rslemos.brill.rules.CURWDRuleFactory;
import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.tagger.ConstantTokenTagger;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public class BrillTrainerBehavior {
	@Test
	public void shouldProduceNoRulesForPerfectBaseCorpus() {
		List<Sentence> sentences = buildText_ToSignUp();
		
		List<RuleFactory> ruleFactories = Collections.emptyList();
		BrillTrainer trainer = new BrillTrainer(ruleFactories);
		
		List<Rule> rules = trainer.train(sentences, sentences).getRules();
		assertEquals(rules.size(), 0);
	}

	@Test
	public void shouldProduce3CURWDRulesForIncompetentBaseTagger() {
		final Object FROM_TAG = "TAG";
		
		List<Sentence> base = buildText(
				buildSentence(
						buildToken("to", FROM_TAG), 
						buildToken("sign", FROM_TAG), 
						buildToken("up", FROM_TAG)
				)
		);
		
		List<Sentence> proof = buildText_ToSignUp();
		
		List<RuleFactory> ruleFactories = Collections.singletonList((RuleFactory)CURWDRuleFactory.INSTANCE);
		BrillTrainer trainer = new BrillTrainer(ruleFactories);
		
		new ConstantTokenTagger(FROM_TAG).tag(new DefaultSentence(base.get(0)));
		
		List<Rule> rules = trainer.train(base, proof).getRules();
		
		assertEquals(rules.size(), 3);
		assertTrue(rules.contains(CURWDRuleFactory.INSTANCE.createRule(FROM_TAG, "TO", "to")));
		assertTrue(rules.contains(CURWDRuleFactory.INSTANCE.createRule(FROM_TAG, "VB", "sign")));
		assertTrue(rules.contains(CURWDRuleFactory.INSTANCE.createRule(FROM_TAG, "RP", "up")));
	}
	
	@Test
	public void shouldNeverConsiderTheSameRuleTwiceForTheSameWord() throws Throwable {
		final String WORD1 = "WORD1";
		final String WORD2 = "WORD2";
		final Object TAG = "TAG";
		
		List<Sentence> base = buildText(
				buildSentence(
						buildToken(WORD1, null), 
						buildToken(WORD2, null)
				)
		);

		List<Sentence> proof = buildText(
				buildSentence(
						buildToken(WORD1, TAG), 
						buildToken(WORD2, TAG)
				)
		);

		class IdentityRule extends AbstractRule {
			private final String name;

			protected IdentityRule(String name) {
				super(null, TAG);
				this.name = name;
			}

			@Override
			public boolean equals(Object o) {
				return this == o;
			}

			@Override
			public String toString() {
				return name;
			}
		}
		
		class WordRule extends IdentityRule {
			private final String word;

			protected WordRule(String name, String word) {
				super(name);
				this.word = word;
			}

			@Override
			public boolean matches(Context context) {
				return (context.getToken(0).get(Token.WORD) == word) && super.matches(context);
			}
		}
		
		class TheFactory implements RuleFactory {
			private final Rule rule1;
			private final Rule rule2;

			protected TheFactory(Rule rule1, Rule rule2) {
				this.rule1 = rule1;
				this.rule2 = rule2;
			}

			public Collection<Rule> create(Context context, Token target) {
				String word = (String) target.get(Token.WORD);
				
				if (word == WORD1)
					return Collections.singleton(rule1);
				
				if (word == WORD2)
					return Collections.singleton(rule2);
				
				throw new RuntimeException();
			}
		}
		
		// rule1 will be returned thrice (factory1_a, factory1_b, factory1_c) for token1
		// rule1_x will be returned once (factory1_x) for token1
		// rule2 will be returned one time for each token
		Rule rule1 = new WordRule("rule1", WORD1);
		Rule rule1_a = new WordRule("rule1_a", WORD2);
		Rule rule1_b = new WordRule("rule1_b", WORD2);
		Rule rule1_c = new WordRule("rule1_c", WORD2);
		Rule rule2 = new IdentityRule("rule2");

		RuleFactory factory1_a = new TheFactory(rule1, rule1_a);
		RuleFactory factory1_b = new TheFactory(rule1, rule1_b);
		RuleFactory factory1_c = new TheFactory(rule1, rule1_c);
		RuleFactory factory2 = new TheFactory(rule2, rule2);
		
		List<RuleFactory> ruleFactories = Arrays.asList(factory1_a, factory1_b, factory1_c, factory2);
		BrillTrainer trainer = new BrillTrainer(ruleFactories);
		
		List<Rule> rules = trainer.train(base, proof).getRules();
		
		assertEquals(rules.get(0), rule2);
	}

	@Test
	public void shouldConsiderEachAndEverySentenceInCorpus() throws Throwable {
		final Object FROM_TAG = "TAG";
		
		List<Sentence> base = buildText(
				buildSentence(
						buildToken("WORD1", FROM_TAG), 
						buildToken("WORD2", FROM_TAG) 
				),
				buildSentence(
						buildToken("WORD3", FROM_TAG), 
						buildToken("WORD4", FROM_TAG) 
				)
		);
		
		List<Sentence> proof = buildText(
				buildSentence(
						buildToken("WORD1", "TAG1"), 
						buildToken("WORD2", "TAG2") 
				),
				buildSentence(
						buildToken("WORD3", "TAG3"), 
						buildToken("WORD4", "TAG4") 
				)
		);
		
		List<RuleFactory> ruleFactories = Collections.singletonList((RuleFactory)CURWDRuleFactory.INSTANCE);
		BrillTrainer trainer = new BrillTrainer(ruleFactories);
		
		List<Rule> rules = trainer.train(base, proof).getRules();
		
		assertEquals(rules.size(), 4);
		assertTrue(rules.contains(CURWDRuleFactory.INSTANCE.createRule(FROM_TAG, "TAG1", "WORD1")));
		assertTrue(rules.contains(CURWDRuleFactory.INSTANCE.createRule(FROM_TAG, "TAG2", "WORD2")));
		assertTrue(rules.contains(CURWDRuleFactory.INSTANCE.createRule(FROM_TAG, "TAG3", "WORD3")));
		assertTrue(rules.contains(CURWDRuleFactory.INSTANCE.createRule(FROM_TAG, "TAG4", "WORD4")));
	}
	
	@Test
	public void shouldConsiderForNegativeScoreEachAndEverySentenceInCorpus() throws Throwable {
		final Object FROM_TAG = "TAG";
		
		List<Sentence> base = buildText(
				buildSentence(
						buildToken("WORD1", FROM_TAG), 
						buildToken("WORD2", FROM_TAG) 
				),
				buildSentence(
						buildToken("WORD2", FROM_TAG), 
						buildToken("WORD2", FROM_TAG) 
				)
		);
		
		List<Sentence> proof = buildText(
				buildSentence(
						buildToken("WORD1", "TAG1"), 
						buildToken("WORD2", "TAG2") 
				),
				buildSentence(
						buildToken("WORD2", "TAG"), 
						buildToken("WORD2", "TAG") 
				)
		);
		
		List<RuleFactory> ruleFactories = Collections.singletonList((RuleFactory)CURWDRuleFactory.INSTANCE);
		BrillTrainer trainer = new BrillTrainer(ruleFactories);
		
		List<Rule> rules = trainer.train(base, proof).getRules();
		
		assertEquals(rules.size(), 1);
		assertTrue(rules.contains(CURWDRuleFactory.INSTANCE.createRule(FROM_TAG, "TAG1", "WORD1")));
	}
	
	public static List<Sentence> buildText_ToSignUp() {
		return buildText(
				buildSentence(
						buildToken("to", "TO"), 
						buildToken("sign", "VB"), 
						buildToken("up", "RP")
				)
		);
	}
}
