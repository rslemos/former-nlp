package br.eti.rslemos.brill.events;

import static org.mockito.Matchers.*;

import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.tagger.Sentence;

public class BrillTrainerCustomMatchers {
	private BrillTrainerCustomMatchers() {}
	
	public static BrillTrainerEvent anyEvent() {
		return anyObject();
	}

	public static Matcher<BrillTrainerEvent> matchesEvent(
			final Matcher<BrillTrainer> sourceMatcher, 
			final Matcher<? super List<Sentence>> proofCorpusMatcher,
			final Matcher<? super List<Sentence>> workingCorpusMatcher,
			final Matcher<Integer> currentSentenceIndexMatcher,
			final Matcher<? super Sentence> currentSentenceMatcher) {

		return new BaseMatcher<BrillTrainerEvent>() {
			@Override
			public boolean matches(Object item) {
				if (!(item instanceof BrillTrainerEvent))
					return false;
				
				BrillTrainerEvent other = (BrillTrainerEvent) item;
				
				return 
					sourceMatcher.matches(other.getSource()) &&
					proofCorpusMatcher.matches(other.getProofCorpus()) &&
					workingCorpusMatcher.matches(other.getWorkingCorpus()) &&
					currentSentenceIndexMatcher.matches(other.getCurrentSentenceIndex()) &&
					currentSentenceMatcher.matches(other.getCurrentSentence());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(BrillTrainerEvent.class.getName());
				description.appendText("(");
				description.appendText("source ").appendDescriptionOf(sourceMatcher).appendText(", ");
				description.appendText("proofCorpus ").appendDescriptionOf(proofCorpusMatcher).appendText(", ");
				description.appendText("workingCorpus ").appendDescriptionOf(workingCorpusMatcher).appendText(", ");
				description.appendText("currentSentenceIndex ").appendDescriptionOf(currentSentenceIndexMatcher).appendText(", ");
				description.appendText("currentSentence ").appendDescriptionOf(currentSentenceMatcher);
				description.appendText(")");
			}
		};
	}
}
