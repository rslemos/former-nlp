package br.eti.rslemos.brill.events;

import static br.eti.rslemos.brill.events.BrillScoringStrategyListenerDispatcher.listener;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.BrillScoringStrategy;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;
import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;
import br.eti.rslemos.brill.events.BrillScoringStrategyListenerDispatcher.BrillScoringStrategyListener;
import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;

public class BrillScoringStrategyEventsBehavior {
	@Test
	public void shouldCaptureInitialization() {
		listener = mock(BrillScoringStrategyListener.class);;
		
		BrillScoringStrategy strategy = new BrillScoringStrategy();
		
		verify(listener, never()).beforeSetTrainingContext((BrillScoringStrategy) anyObject(), (TrainingContext) anyObject());
		verify(listener, never()).afterSetTrainingContext((BrillScoringStrategy) anyObject(), (TrainingContext) anyObject());
		
		TrainingContext trainingContext = mock(TrainingContext.class);
		strategy.setTrainingContext(trainingContext);
		
		verify(listener, times(1)).beforeSetTrainingContext(strategy, trainingContext);
		verify(listener, times(1)).afterSetTrainingContext(strategy, trainingContext);
	}
	
	@Test
	public void shouldCaptureRuleSubmission() throws Throwable {
		listener = mock(BrillScoringStrategyListener.class);;
		
		BrillScoringStrategy strategy = new BrillScoringStrategy();
		TrainingContext trainingContext = mock(TrainingContext.class);
		
		List<List<Token>> proofCorpus = Collections.emptyList();
		setProofCorpus(trainingContext, proofCorpus);
		
		
		strategy.setTrainingContext(trainingContext);

		verify(listener, never()).beforeCompute((BrillScoringStrategy) anyObject(), (Rule) anyObject());
		verify(listener, never()).afterCompute((BrillScoringStrategy) anyObject(), (Rule) anyObject(), anyInt());
		
		Rule rule = mock(Rule.class);
		strategy.compute(rule, 0);

		verify(listener, times(1)).beforeCompute(strategy, rule);
		verify(listener, times(1)).afterCompute(strategy, rule, 0);
	}

	@Test
	public void shouldCaptureRuleSubmissionWithPositiveScore() throws Exception {
		listener = mock(BrillScoringStrategyListener.class);;
		
		BrillScoringStrategy strategy = new BrillScoringStrategy();
		TrainingContext trainingContext = mock(TrainingContext.class);
		
		Token proofToken = mock(Token.class);
		when(proofToken.getTag()).thenReturn("TAG");
		
		List<Token> proofSentence = Collections.singletonList(proofToken);
		List<List<Token>> proofCorpus = Collections.singletonList(proofSentence);
		
		setProofCorpus(trainingContext, proofCorpus);
		
		BufferingContext trainingSentence = mock(BufferingContext.class);
		BufferingContext[] trainingCorpus = new BufferingContext[] { trainingSentence };
		setTrainingCorpus(trainingContext, trainingCorpus);
		
		strategy.setTrainingContext(trainingContext);

		verify(listener, never()).beforeCompute((BrillScoringStrategy) anyObject(), (Rule) anyObject());
		verify(listener, never()).afterCompute((BrillScoringStrategy) anyObject(), (Rule) anyObject(), anyInt());
		
		Rule rule = mock(Rule.class);
		when(rule.getTo()).thenReturn("TAG");
		when(rule.matches(trainingSentence)).thenReturn(true);

		strategy.compute(rule, 1);

		verify(listener, times(1)).beforeCompute(strategy, rule);
		verify(listener, times(1)).afterCompute(strategy, rule, 1);
	}

	@Test
	public void shouldCaptureEachSentence() throws Exception {
		listener = mock(BrillScoringStrategyListener.class);;
		
		BrillScoringStrategy strategy = new BrillScoringStrategy();
		TrainingContext trainingContext = mock(TrainingContext.class);
		
		Token proofToken = mock(Token.class);
		when(proofToken.getTag()).thenReturn("TAG");
		
		List<Token> proofSentence = Collections.singletonList(proofToken);
		List<List<Token>> proofCorpus = Collections.singletonList(proofSentence);
		
		setProofCorpus(trainingContext, proofCorpus);
		
		BufferingContext trainingSentence = mock(BufferingContext.class);
		BufferingContext[] trainingCorpus = new BufferingContext[] { trainingSentence };
		setTrainingCorpus(trainingContext, trainingCorpus);
		
		strategy.setTrainingContext(trainingContext);

		verify(listener, never()).beforeSentence((BrillScoringStrategy) anyObject(), (List<Token>) anyObject(), (BufferingContext)anyObject());
		verify(listener, never()).afterSentence((BrillScoringStrategy) anyObject(), (List<Token>) anyObject(), (BufferingContext)anyObject());
		
		Rule rule = mock(Rule.class);
		when(rule.getTo()).thenReturn("TAG");
		when(rule.matches(trainingSentence)).thenReturn(true);

		strategy.compute(rule, 1);

		verify(listener, times(1)).beforeSentence(strategy, proofSentence, trainingSentence);
		verify(listener, times(1)).afterSentence(strategy, proofSentence, trainingSentence);
	}
	
	@Test
	public void shouldCaptureEachToken() throws Exception {
		listener = mock(BrillScoringStrategyListener.class);;
		
		BrillScoringStrategy strategy = new BrillScoringStrategy();
		TrainingContext trainingContext = mock(TrainingContext.class);
		
		Token proofToken = mock(Token.class);
		when(proofToken.getTag()).thenReturn("TAG");
		
		List<Token> proofSentence = Collections.singletonList(proofToken);
		List<List<Token>> proofCorpus = Collections.singletonList(proofSentence);
		
		setProofCorpus(trainingContext, proofCorpus);
		
		BufferingContext trainingSentence = mock(BufferingContext.class);
		
		BufferingContext[] trainingCorpus = new BufferingContext[] { trainingSentence };
		setTrainingCorpus(trainingContext, trainingCorpus);
		
		strategy.setTrainingContext(trainingContext);

		verify(listener, never()).beforeToken((BrillScoringStrategy) anyObject(), (Token) anyObject(), (BufferingContext)anyObject());
		verify(listener, never()).afterToken((BrillScoringStrategy) anyObject(), (Token) anyObject(), (BufferingContext)anyObject());
		
		Rule rule = mock(Rule.class);
		when(rule.getTo()).thenReturn("TAG");
		when(rule.matches(trainingSentence)).thenReturn(true);

		strategy.compute(rule, 1);

		verify(listener, times(1)).beforeToken(strategy, proofToken, trainingSentence);
		verify(listener, times(1)).afterToken(strategy, proofToken, trainingSentence);
	}

	private static void setProofCorpus(TrainingContext trainingContext, List<List<Token>> proofCorpus) throws Exception {
		Field proofCorpusField = TrainingContext.class.getDeclaredField("proofCorpus");
		proofCorpusField.setAccessible(true);
		proofCorpusField.set(trainingContext, proofCorpus);
	}
	
	private static void setTrainingCorpus(TrainingContext trainingContext, BufferingContext[] trainingCorpus) throws Exception {
		Field proofCorpusField = TrainingContext.class.getDeclaredField("trainingCorpus");
		proofCorpusField.setAccessible(true);
		proofCorpusField.set(trainingContext, trainingCorpus);
	}
	
}
