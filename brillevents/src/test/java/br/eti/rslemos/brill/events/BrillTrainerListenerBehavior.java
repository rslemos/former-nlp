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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.brill.BrillTrainer.Pair;
import br.eti.rslemos.tagger.ConstantTokenTagger;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class BrillTrainerListenerBehavior {
	private BrillTrainer trainer;
	
	@Mock private BrillTrainerListener listener;
	
	private Tagger baseTagger;
	
	private Token[][] tokens = {
			{ null, null },
			{ null, null },
	};
	
	private Sentence[] sentences = { null, null };
	
	private List<Sentence> proofCorpus;
	
	

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		trainer = new BrillTrainer();
		
		trainer.addBrillTrainerListener(listener);

		baseTagger = new ConstantTokenTagger("BASE");
		trainer.setBaseTagger(baseTagger);
		
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
	public void shouldNotifyTrainingPrepareStartAndFinish() {
		trainer.train(proofCorpus);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).trainingStart(anyEvent());
		order.verify(listener).trainingCorpusInitialized(eventWithBaseTaggedCorpus());
		order.verify(listener).trainingFinish(anyEvent());
	}

	private static Matcher<BrillTrainerEvent> matchesEvent(
			final Matcher<BrillTrainer> sourceMatcher, 
			final Matcher<? super List<Sentence>> overCorpusMatcher,
			final Matcher<? super List<Sentence>> trainingCorpusMatcher) {

		return new BaseMatcher<BrillTrainerEvent>() {
			@Override
			public boolean matches(Object item) {
				if (!(item instanceof BrillTrainerEvent))
					return false;
				
				BrillTrainerEvent other = (BrillTrainerEvent) item;
				
				return 
					sourceMatcher.matches(other.getSource()) &&
					overCorpusMatcher.matches(other.getOverCorpus()) &&
					trainingCorpusMatcher.matches(other.getTrainingCorpus());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(BrillTrainerEvent.class.getName());
				description.appendText("(");
				description.appendText("source ").appendDescriptionOf(sourceMatcher).appendText(", ");
				description.appendText("overCorpus ").appendDescriptionOf(overCorpusMatcher).appendText(", ");
				description.appendText("trainingCorpus ").appendDescriptionOf(trainingCorpusMatcher);
				description.appendText(")");
			}
		};
	}
	
	private static Matcher<List<Sentence>> sameWordsBaseTag(final List<Sentence> proofCorpus, final Object baseTag) {
		return new BaseMatcher<List<Sentence>>() {
			@Override
			public boolean matches(Object item) {
				if (!(item instanceof List))
					return false;
				
				List<Sentence> other = (List<Sentence>) item;

				for (Pair<Sentence, Sentence> pair : BrillTrainer.pairOf(proofCorpus, other)) {
					if (!sameWords(pair.x, pair.y))
						return false;
					
					if (!tagsAsBaseTag(pair.y))
						return false;
				}
				
				return true;
			}

			private boolean sameWords(Sentence x, Sentence y) {
				if (x.size() != y.size())
					return false;
				
				for (Pair<Token, Token> pair : BrillTrainer.pairOf(x, y)) {
					if (!pair.x.getWord().equals(pair.y.getWord()))
						return false;
				}
				
				return true;
			}

			private boolean tagsAsBaseTag(Sentence y) {
				for (Token token : y) {
					if (token.getTag() != baseTag)
						return false;
				}
				
				return true;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("same words as ").appendValue(proofCorpus);
				description.appendText(" all tagged as ").appendValue(baseTag);
			}
		};
	}

	private BrillTrainerEvent eventWithBaseTaggedCorpus() {
		return argThat(matchesEvent(is(sameInstance(trainer)), is(sameInstance(proofCorpus)), is(sameWordsBaseTag(proofCorpus, "BASE"))));
	}

	private BrillTrainerEvent eventWithProofCorpus() {
		return argThat(matchesEvent(is(sameInstance(trainer)), is(sameInstance(proofCorpus)), is(nullValue(List.class))));
	}

	private static BrillTrainerEvent anyEvent() {
		return anyObject();
	}

}
