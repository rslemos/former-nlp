package br.eti.rslemos.brill.events;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
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
import br.eti.rslemos.tagger.Sentence;

public class BrillTrainerListenerBehavior {
	private BrillTrainer trainer;
	
	@Mock private BrillTrainerListener listener;
	
	private List<Sentence> proofCorpus;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		trainer = new BrillTrainer();
		
		trainer.addBrillTrainerListener(listener);
		
		proofCorpus = Collections.emptyList();
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

	private Matcher<BrillTrainerEvent> matchesEvent(
			Matcher<BrillTrainer> trainer, 
			Matcher<List<Sentence>> overCorpus) {

		return new CustomEventMatcher(trainer, overCorpus);
	}
	
	private static class CustomEventMatcher extends BaseMatcher<BrillTrainerEvent> {

		private final Matcher<BrillTrainer> sourceMatcher;
		private final Matcher<List<Sentence>> overCorpusMatcher;

		public CustomEventMatcher(
				Matcher<BrillTrainer> trainer,
				Matcher<List<Sentence>> overCorpus) {
					this.sourceMatcher = trainer;
					this.overCorpusMatcher = overCorpus;
		}

		@Override
		public boolean matches(Object item) {
			if (!(item instanceof BrillTrainerEvent))
				return false;
			
			BrillTrainerEvent other = (BrillTrainerEvent) item;
			
			return 
				sourceMatcher.matches(other.getSource()) &&
				overCorpusMatcher.matches(other.getOverCorpus());
		}

		@Override
		public void describeTo(Description description) {
			description.appendText(BrillTrainerEvent.class.getName());
			description.appendText("(");
			description.appendText("source ").appendDescriptionOf(sourceMatcher).appendText(", ");
			description.appendText("overCorpus ").appendDescriptionOf(overCorpusMatcher);
			description.appendText(")");
		}
	}

	private BrillTrainerEvent eventWithProofCorpus() {
		return argThat(matchesEvent(is(sameInstance(trainer)), is(sameInstance(proofCorpus))));
	}

	private static BrillTrainerEvent anyEvent() {
		return anyObject();
	}

}
