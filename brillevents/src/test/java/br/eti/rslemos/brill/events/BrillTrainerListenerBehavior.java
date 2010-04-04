package br.eti.rslemos.brill.events;

import static br.eti.rslemos.brill.events.BrillCustomMatchers.*;
import static br.eti.rslemos.brill.events.BrillTrainerCustomMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.brill.events.BrillTrainerCustomMatchers.BrillTrainerEventMatcher;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class BrillTrainerListenerBehavior {
	private BrillTrainer trainer;
	
	@Mock private BrillTrainerListener listener;
	
	@Mock private Tagger baseTagger;
	
	private Token[][] tokens = {
			{ null, null },
			{ null, null },
	};
	
	private Sentence[] sentences = { null, null };
	
	private List<Sentence> proofCorpus;
	
	

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		for (int i = 0; i < tokens.length; i++) {
			for (int j = 0; j < tokens[i].length; j++) {
				tokens[i][j] = mock(Token.class, i + ", " + j);
				when(tokens[i][j].getWord()).thenReturn("word " + i + ", " + j);
				when(tokens[i][j].getTag()).thenReturn("tag " + i + ", " + j);
			}
		}
	
		for (int i = 0; i < sentences.length; i++) {
			sentences[i] = new DefaultSentence(Arrays.asList(tokens[i]));
		}
		
		proofCorpus = Arrays.asList(sentences);

		trainer = new BrillTrainer();
		
		trainer.addBrillTrainerListener(listener);

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Sentence sentence = (Sentence) invocation.getArguments()[0];
				for (Token token : sentence) {
					token.setTag("BASE");
				}
				return null;
			}
		}).when(baseTagger).tag(anySentence());
		
		
		
		
		//spy(new ConstantTokenTagger("BASE"));
		trainer.setBaseTagger(baseTagger);
		
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
					.withWorkingCorpus(is(allOf(sameWords(proofCorpus), sentencesTaggedAs("BASE"))))));
		
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
					.withCurrentSentence(allOf(sameWords(sentences[0]), taggedAs("BASE")))));
		
		order.verify(listener).baseTaggingStart(argThat(
				isBasicBrillTrainerEvent()
					.withCurrentSentenceIndex(1)
					.withCurrentSentence(sameWords(sentences[1]))));
		
		order.verify(baseTagger).tag(argThat(is(sameWords(sentences[1]))));
		order.verify(listener).baseTaggingFinish(argThat(
				isBasicBrillTrainerEvent()
					.withCurrentSentenceIndex(1)
					.withCurrentSentence(allOf(sameWords(sentences[1]), taggedAs("BASE")))));

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
		order.verify(listener).ruleDiscoveryFinish(argThat(isBasicInitializedBrillTrainerEvent()));
		
		order.verify(listener).trainingFinish(anyEvent());
		
		order.verify(listener, never()).ruleDiscoveryStart(anyEvent());
		order.verify(listener, never()).ruleDiscoveryFinish(anyEvent());
	}

	@Test
	public void shouldNotifyNewRuleDiscovered() {
		trainer.train(proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).ruleDiscoveryStart(anyEvent());

		order.verify(listener).newRuleDiscovered(argThat(
				isBasicInitializedBrillTrainerEvent()
					.withFoundRules(whichSize(is(equalTo(0))))
					.justFoundRule(null)
				));
		
		order.verify(listener).ruleDiscoveryFinish(anyEvent());
	}

	private BrillTrainerEventMatcher isBasicBrillTrainerEvent() {
		return isBrillTrainerEvent().from(trainer).proofedByCorpusEqualsTo(proofCorpus);
	}

	private BrillTrainerEventMatcher isBasicInitializedBrillTrainerEvent() {
		return isBasicBrillTrainerEvent().withWorkingCorpus(is(sameWords(proofCorpus)));
	}
}
