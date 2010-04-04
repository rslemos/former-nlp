package br.eti.rslemos.brill.events;

import static org.hamcrest.CoreMatchers.*;
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

	public static BrillTrainerEventMatcher isBrillTrainerEvent() {
		// this forms an empty event matcher
		return new BrillTrainerEventMatcher()
			.withSource(is(nullValue(BrillTrainer.class)))
			.withProofCorpus(is(nullValue(List.class)))
			.withWorkingCorpus(is(nullValue(List.class)))
			.withCurrentSentenceIndex(is(equalTo(-1)))
			.withCurrentSentence(is(nullValue(Sentence.class)));
	}

	public static class BrillTrainerEventMatcher extends BaseMatcher<BrillTrainerEvent> {
		private Matcher<BrillTrainer> sourceMatcher;
		private Matcher<? super List<Sentence>> workingCorpusMatcher;
		private Matcher<? super Sentence> currentSentenceMatcher;
		private Matcher<Integer> currentSentenceIndexMatcher;
		private Matcher<? super List<Sentence>> proofCorpusMatcher;

		public BrillTrainerEventMatcher() {}
		
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

		public BrillTrainerEventMatcher withSource(Matcher<BrillTrainer> sourceMatcher) {
			this.sourceMatcher = sourceMatcher;
			return this;
		}

		public BrillTrainerEventMatcher withProofCorpus(Matcher<? super List<Sentence>> proofCorpusMatcher) {
			this.proofCorpusMatcher = proofCorpusMatcher;
			return this;
		}

		public BrillTrainerEventMatcher withWorkingCorpus(Matcher<? super List<Sentence>> workingCorpusMatcher) {
			this.workingCorpusMatcher = workingCorpusMatcher;
			return this;
		}

		public BrillTrainerEventMatcher withCurrentSentenceIndex(Matcher<Integer> currentSentenceIndexMatcher) {
			this.currentSentenceIndexMatcher = currentSentenceIndexMatcher;
			return this;
		}

		public BrillTrainerEventMatcher withCurrentSentence(Matcher<? super Sentence> currentSentenceMatcher) {
			this.currentSentenceMatcher = currentSentenceMatcher;
			return this;
		}

		public BrillTrainerEventMatcher from(BrillTrainer trainer) {
			return withSource(is(sameInstance(trainer)));
		}

		public BrillTrainerEventMatcher proofedBy(List<Sentence> proofCorpus) {
			return withProofCorpus(is(sameInstance(proofCorpus)));
		}

		public BrillTrainerEventMatcher proofedByCorpusEqualsTo(List<Sentence> proofCorpus) {
			return withProofCorpus(is(equalTo(proofCorpus)));
		}

		public BrillTrainerEventMatcher withCurrentSentenceIndex(int index) {
			return withCurrentSentenceIndex(is(equalTo(index)));
		}

	}
}
