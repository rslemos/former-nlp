/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
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
package br.eti.rslemos.brill.events;

import static br.eti.rslemos.brill.events.BrillCustomMatchers.sameWords;
import static br.eti.rslemos.brill.events.BrillCustomMatchers.sentencesTaggedAs;
import static br.eti.rslemos.brill.events.BrillCustomMatchers.whichSize;
import static br.eti.rslemos.brill.events.BrillTrainerCustomMatchers.anyEvent;
import static br.eti.rslemos.brill.events.BrillTrainerCustomMatchers.isBrillTrainerEvent;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.events.BrillTrainerCustomMatchers.BrillTrainerEventMatcher;
import br.eti.rslemos.brill.rules.CURWD;
import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

@SuppressWarnings("unchecked")
public class BrillTrainerListenerBehavior {
	private static final Object BASE_TAG = "BASE";

	private BrillTrainer trainer;
	
	@Mock private BrillTrainerListener listener;
	
	private static Token makeToken(String word, Object tag) {
		Token token = new DefaultToken(word);
		token.put(Token.POS, tag);
		return token;
	}
	
	private Token[][] baseTokens = {
			{ makeToken("W00", BASE_TAG), makeToken("W01", BASE_TAG) },
			{ makeToken("W00", BASE_TAG), makeToken("W11", BASE_TAG) },
	};
	
	private Sentence[] baseSentences = { 
			new DefaultSentence(baseTokens[0]),
			new DefaultSentence(baseTokens[1]),
	};
	
	private List<Sentence> baseCorpus = Arrays.asList(baseSentences);

	private Token[][] proofTokens = {
			{ makeToken("W00", "T00"), makeToken("W01", "T01") },
			{ makeToken("W00", "T00"), makeToken("W11", "T11") },
	};
	
	private Sentence[] proofSentences = { 
			new DefaultSentence(proofTokens[0]),
			new DefaultSentence(proofTokens[1]),
	};
	
	private List<Sentence> proofCorpus = Arrays.asList(proofSentences);

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		trainer = new BrillTrainer();
		
		trainer.addBrillTrainerListener(listener);

		trainer.setRuleFactories(Collections.singletonList((RuleFactory)new CURWD.Factory()));
		
		/*
		 * Effective rule discovery should be avoided until strictly necessary.
		 * 
		 * Mockito postpones the verification phase until the system was exercised,
		 * but BrillTrainer reuses it's working corpus (well, it is this way because
		 * that is the way it should be). So when verifying it is already too late.
		 * 
		 * Avoiding discovery implies immutability on working corpus.
		 */
		trainer.setThreshold(3);
		
	}
	
	@Test
	public void shouldAcceptListeners() {
		//trainer.addBrillTrainerListener(listener);
		trainer.removeBrillTrainerListener(listener);
	}

	@Test
	public void shouldNotifyTrainingStartAndFinish() {
		trainer.train(baseCorpus, proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).trainingStart(argThat(isBrillTrainerEvent().from(trainer).proofedBy(proofCorpus)));
		order.verify(listener).trainingFinish(argThat(isBrillTrainerEvent().from(trainer).proofedBy(proofCorpus)));

		order.verify(listener, never()).trainingStart(anyEvent());
		order.verify(listener, never()).trainingFinish(anyEvent());
	}

	@Test
	public void shouldNotifyTrainingCorpusInitialization() {
		trainer.train(baseCorpus, proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).trainingStart(anyEvent());
		order.verify(listener).workingCorpusInitializationStart(argThat(isBasicBrillTrainerEvent()));
		
		order.verify(listener).workingCorpusInitializationFinish(argThat(
				isBasicBrillTrainerEvent()
					.withWorkingCorpus(is(allOf(sameWords(proofCorpus), sentencesTaggedAs(BASE_TAG))))));
		
		order.verify(listener).trainingFinish(anyEvent());

		order.verify(listener, never()).workingCorpusInitializationStart(anyEvent());
		order.verify(listener, never()).workingCorpusInitializationFinish(anyEvent());
	}

	@Test
	public void shouldNotifyRuleDiscovery() {
		trainer.train(baseCorpus, proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).workingCorpusInitializationFinish(anyEvent());
		
		order.verify(listener).ruleDiscoveryPhaseStart(argThat(isBasicInitializedBrillTrainerEvent()));		
		order.verify(listener).ruleDiscoveryPhaseFinish(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withFoundRules(whichSize(is(equalTo(0))))));
		
		order.verify(listener).trainingFinish(anyEvent());
		
		order.verify(listener, never()).ruleDiscoveryPhaseStart(anyEvent());
		order.verify(listener, never()).ruleDiscoveryPhaseFinish(anyEvent());
	}

	@Test
	public void shouldNotifyRuleDiscoveryRound() {
		trainer.train(baseCorpus, proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).ruleDiscoveryPhaseStart(anyEvent());	
		
		order.verify(listener).ruleDiscoveryRoundStart(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withRound()
					.withFoundRules(whichSize(is(equalTo(0))))
		));
		
		order.verify(listener).ruleDiscoveryRoundFinish(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withRound()
					.withFoundRules(whichSize(is(equalTo(0))))
		));
		
		order.verify(listener).ruleDiscoveryPhaseFinish(anyEvent());		
	}

	@Test
	public void shouldNotifyRuleDiscoveryRoundWithNewRule() {
		final Rule rule = CURWD.Factory.INSTANCE.createRule(BASE_TAG, "T00", "W00");
		
		trainer.setThreshold(2);
		
		trainer.train(baseCorpus, proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).ruleDiscoveryPhaseStart(anyEvent());	
		
		order.verify(listener).ruleDiscoveryRoundStart(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withRound()
					// should be an empty list
					// but by the time it is verified, the list already has rules
					.withFoundRules(is(any(List.class)))
		));
		
		order.verify(listener).ruleDiscoveryRoundFinish(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withRound()
					// should be an empty list
					// but by the time it is verified, the list already has rules
					.withFoundRules(is(any(List.class)))
					.justFoundRule(rule)
		));
		
		order.verify(listener).ruleDiscoveryPhaseFinish(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withFoundRules(is(equalTo(Collections.singletonList(rule))))));
	}

	@Test
	public void shouldNotifyPossibleRulesProduction() {
		trainer.setThreshold(2);
		
		trainer.train(baseCorpus, proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).ruleDiscoveryRoundStart(anyEvent());

		order.verify(listener).possibleRulesProductionStart(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withRound()
		));
		
		order.verify(listener).possibleRulesProductionFinish(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withRound()
					.withPossibleRules(is(not(nullValue(Collection.class))))
		));
		
		order.verify(listener).ruleDiscoveryRoundFinish(anyEvent());
	}

	@Test
	public void shouldNotifyBestRuleSelection() {
		trainer.setThreshold(2);
		
		trainer.train(baseCorpus, proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).possibleRulesProductionFinish(anyEvent());
		
		order.verify(listener).bestRuleSelectionStart(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withRound()
					.withPossibleRules(is(not(nullValue(Collection.class))))
		));
		
		order.verify(listener).bestRuleSelectionFinish(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withRound()
					.withPossibleRules(is(not(nullValue(Collection.class))))
		));
		
		order.verify(listener).ruleDiscoveryRoundFinish(anyEvent());
	}

	private BrillTrainerEventMatcher isBasicBrillTrainerEvent() {
		return isBrillTrainerEvent().from(trainer).proofedByCorpusEqualsTo(proofCorpus);
	}

	private BrillTrainerEventMatcher isBasicInitializedBrillTrainerEvent() {
		return isBasicBrillTrainerEvent().withWorkingCorpus(is(sameWords(proofCorpus)));
	}
}
