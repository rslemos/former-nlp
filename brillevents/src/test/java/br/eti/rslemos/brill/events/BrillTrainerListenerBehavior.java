package br.eti.rslemos.brill.events;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.brill.BrillTrainer.Pair;
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
		
		order.verify(listener).trainingStart(eventWithProofCorpus());
		order.verify(listener).trainingFinish(eventWithProofCorpus());
	}

	@Test
	public void shouldNotifyBaseTaggerApplicationAndTrainingCorpusInitialization() {
		trainer.train(proofCorpus);
		
		InOrder order = inOrder(listener, baseTagger);
		
		order.verify(listener).trainingStart(anyEvent());
		order.verify(baseTagger).tag(argThat(is(sameWords(sentences[0]))));
		order.verify(listener).baseTaggerApplied(eventWithSentenceWordsAndBaseTag(sentences[0]));
		order.verify(baseTagger).tag(argThat(is(sameWords(sentences[1]))));
		order.verify(listener).baseTaggerApplied(eventWithSentenceWordsAndBaseTag(sentences[1]));
		order.verify(listener).trainingCorpusInitialized(eventWithBaseTaggedCorpus());
		order.verify(listener).trainingFinish(anyEvent());
	}

	private static boolean sameWords(Sentence x, Sentence y) {
		if (x.size() != y.size())
			return false;
		
		for (Pair<Token, Token> pair : BrillTrainer.pairOf(x, y)) {
			if (!pair.x.getWord().equals(pair.y.getWord()))
				return false;
		}
		
		return true;
	}

	private static boolean sameTag(Object baseTag, Sentence y) {
		for (Token token : y) {
			if (token.getTag() != baseTag)
				return false;
		}
		
		return true;
	}


	private static Matcher<Sentence> sameWords(final Sentence sentence) {
		return new BaseMatcher<Sentence>() {
			@Override
			public boolean matches(Object item) {
				if (!(item instanceof Sentence))
					return false;
				
				Sentence other = (Sentence) item;

				return sameWords(sentence, other);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("same words as ").appendValue(sentence);
			}
		};
	}

	private static Matcher<Sentence> taggedAs(final Object baseTag) {
		return new BaseMatcher<Sentence>() {
			@Override
			public boolean matches(Object item) {
				if (!(item instanceof Sentence))
					return false;
				
				Sentence other = (Sentence) item;

				return sameTag(baseTag, other);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("tagged as ").appendValue(baseTag);
			}
		};
	}


	private static Matcher<BrillTrainerEvent> matchesEvent(
			final Matcher<BrillTrainer> sourceMatcher, 
			final Matcher<? super List<Sentence>> overCorpusMatcher,
			final Matcher<? super List<Sentence>> trainingCorpusMatcher,
			final Matcher<? super Sentence> lastProcessedSentenceMatcher) {

		return new BaseMatcher<BrillTrainerEvent>() {
			@Override
			public boolean matches(Object item) {
				if (!(item instanceof BrillTrainerEvent))
					return false;
				
				BrillTrainerEvent other = (BrillTrainerEvent) item;
				
				return 
					sourceMatcher.matches(other.getSource()) &&
					overCorpusMatcher.matches(other.getOverCorpus()) &&
					trainingCorpusMatcher.matches(other.getTrainingCorpus()) &&
					lastProcessedSentenceMatcher.matches(other.getLastProcessedSentence());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(BrillTrainerEvent.class.getName());
				description.appendText("(");
				description.appendText("source ").appendDescriptionOf(sourceMatcher).appendText(", ");
				description.appendText("overCorpus ").appendDescriptionOf(overCorpusMatcher).appendText(", ");
				description.appendText("trainingCorpus ").appendDescriptionOf(trainingCorpusMatcher).appendText(", ");
				description.appendText("lastProcessedSentence ").appendDescriptionOf(lastProcessedSentenceMatcher);
				description.appendText(")");
			}
		};
	}
	
	private static Matcher<List<Sentence>> sameWords(final List<Sentence> proofCorpus) {
		return new BaseMatcher<List<Sentence>>() {
			@Override
			public boolean matches(Object item) {
				if (!(item instanceof List))
					return false;
				
				List<Sentence> other = (List<Sentence>) item;

				for (Pair<Sentence, Sentence> pair : BrillTrainer.pairOf(proofCorpus, other)) {
					if (!sameWords(pair.x, pair.y))
						return false;
				}
				
				return true;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("same words as ").appendValue(proofCorpus);
			}
		};
	}

	private static Matcher<List<Sentence>> sentencesTaggedAs(final Object baseTag) {
		return new BaseMatcher<List<Sentence>>() {
			@Override
			public boolean matches(Object item) {
				if (!(item instanceof List))
					return false;
				
				List<Sentence> other = (List<Sentence>) item;

				for (Sentence sentence : other) {
					if (!sameTag(baseTag, sentence))
						return false;
				}
				
				return true;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("tagged as ").appendValue(baseTag);
			}
		};
	}

	private BrillTrainerEvent eventWithSentenceWordsAndBaseTag(Sentence sentence) {
		return argThat(matchesEvent(is(sameInstance(trainer)), is(sameInstance(proofCorpus)), is(nullValue(List.class)), is(allOf(sameWords(sentence), taggedAs("BASE")))));
	}

	private BrillTrainerEvent eventWithBaseTaggedCorpus() {
		return argThat(matchesEvent(is(sameInstance(trainer)), is(sameInstance(proofCorpus)), is(allOf(sameWords(proofCorpus), sentencesTaggedAs("BASE"))), is(nullValue(Sentence.class))));
	}

	private BrillTrainerEvent eventWithProofCorpus() {
		return argThat(matchesEvent(is(sameInstance(trainer)), is(sameInstance(proofCorpus)), is(nullValue(List.class)), is(nullValue(Sentence.class))));
	}

	private static BrillTrainerEvent anyEvent() {
		return anyObject();
	}

	private static Sentence anySentence() {
		return anyObject();
	}

}
