package br.eti.rslemos.brill.events;

import static br.eti.rslemos.brill.events.BrillCustomMatchers.*;
import static br.eti.rslemos.brill.events.BrillTrainerCustomMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.events.BrillTrainerCustomMatchers.BrillTrainerEventMatcher;
import br.eti.rslemos.brill.rules.CURWDRule;
import br.eti.rslemos.brill.rules.CURWDRuleFactory;
import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class BrillTrainerListenerBehavior {
	private static final Object BASE_TAG = "BASE";

	private BrillTrainer trainer;
	
	@Mock private BrillTrainerListener listener;
	
	@Mock private Tagger baseTagger;
	
	private Token[][] tokens = {
			{ new DefaultToken("W00").setTag("T00"), new DefaultToken("W01").setTag("T01") },
			{ new DefaultToken("W00").setTag("T00"), new DefaultToken("W11").setTag("T11") },
	};
	
	private Sentence[] sentences = { 
			new DefaultSentence(tokens[0]),
			new DefaultSentence(tokens[1]),
	};
	
	private List<Sentence> proofCorpus = Arrays.asList(sentences);

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		trainer = new BrillTrainer();
		
		trainer.addBrillTrainerListener(listener);

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Sentence sentence = (Sentence) invocation.getArguments()[0];
				for (Token token : sentence) {
					token.setTag(BASE_TAG);
				}
				return null;
			}
		}).when(baseTagger).tag(anySentence());
		
		trainer.setBaseTagger(baseTagger);
		trainer.setRuleFactories(Collections.singletonList((RuleFactory)new CURWDRuleFactory()));
		
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
		trainer.train(proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).trainingStart(argThat(isBrillTrainerEvent().from(trainer).proofedBy(proofCorpus)));
		order.verify(listener).trainingFinish(argThat(isBrillTrainerEvent().from(trainer).proofedBy(proofCorpus)));

		order.verify(listener, never()).trainingStart(anyEvent());
		order.verify(listener, never()).trainingFinish(anyEvent());
	}

	@Test
	public void shouldNotifyTrainingCorpusInitialization() {
		trainer.train(proofCorpus);
		
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
	public void shouldNotifyBaseTaggerApplication() {
		trainer.train(proofCorpus);
		
		InOrder order = inOrder(listener, baseTagger);
		
		order.verify(listener).workingCorpusInitializationStart(anyEvent());
		
		order.verify(listener).baseTaggingStart(argThat(
				isBasicBrillTrainerEvent()
					.withCurrentSentenceIndex(0)
					.withCurrentSentence(sameWords(sentences[0]))));
		
		order.verify(baseTagger).tag(argThat(is(sameWords(sentences[0]))));
		order.verify(listener).baseTaggingFinish(argThat(
				isBasicBrillTrainerEvent()
					.withCurrentSentenceIndex(0)
					.withCurrentSentence(allOf(sameWords(sentences[0]), taggedAs(BASE_TAG)))));
		
		order.verify(listener).baseTaggingStart(argThat(
				isBasicBrillTrainerEvent()
					.withCurrentSentenceIndex(1)
					.withCurrentSentence(sameWords(sentences[1]))));
		
		order.verify(baseTagger).tag(argThat(is(sameWords(sentences[1]))));
		order.verify(listener).baseTaggingFinish(argThat(
				isBasicBrillTrainerEvent()
					.withCurrentSentenceIndex(1)
					.withCurrentSentence(allOf(sameWords(sentences[1]), taggedAs(BASE_TAG)))));

		order.verify(listener).workingCorpusInitializationFinish(anyEvent());
		
		order.verify(baseTagger, never()).tag(anySentence());
		order.verify(listener, never()).baseTaggingStart(anyEvent());
		order.verify(listener, never()).baseTaggingFinish(anyEvent());
	}

	@Test
	public void shouldNotifyRuleDiscovery() {
		trainer.train(proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).workingCorpusInitializationFinish(anyEvent());
		
		order.verify(listener).ruleDiscoveryStart(argThat(isBasicInitializedBrillTrainerEvent()));		
		order.verify(listener).ruleDiscoveryFinish(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withFoundRules(whichSize(is(equalTo(0))))));
		
		order.verify(listener).trainingFinish(anyEvent());
		
		order.verify(listener, never()).ruleDiscoveryStart(anyEvent());
		order.verify(listener, never()).ruleDiscoveryFinish(anyEvent());
	}

	@Test
	public void shouldNotifyNewRuleDiscovered() {
		final Rule rule = new CURWDRule(BASE_TAG, "T00", "W00");
		
		trainer.setThreshold(2);
		
		trainer.train(proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).ruleDiscoveryStart(anyEvent());

		order.verify(listener).newRuleDiscovered(argThat(
				isBasicInitializedBrillTrainerEvent()
					// should be an empty list
					// but by the time it is verified, the list already has rules
					.withFoundRules(is(any(List.class)))
					.justFoundRule(rule)
				));
		
		order.verify(listener, never()).newRuleDiscovered(anyEvent());
		
		order.verify(listener).ruleDiscoveryFinish(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withFoundRules(is(equalTo(Collections.singletonList(rule))))));
	}

	private BrillTrainerEventMatcher isBasicBrillTrainerEvent() {
		return isBrillTrainerEvent().from(trainer).proofedByCorpusEqualsTo(proofCorpus);
	}

	private BrillTrainerEventMatcher isBasicInitializedBrillTrainerEvent() {
		return isBasicBrillTrainerEvent().withWorkingCorpus(is(sameWords(proofCorpus)));
	}
}
