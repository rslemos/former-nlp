package br.eti.rslemos.brill.events;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.*;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerCustomMatchers {
	private BrillTaggerCustomMatchers() {}
	
	public static BrillTaggerEvent anyEvent() {
		return anyObject();
	}

	public static BrillTaggerEventMatcher isBrillTaggerEvent() {
		// this forms an empty event matcher
		return new BrillTaggerEventMatcher()
			.withSource(is(nullValue(BrillTagger.class)))
			.withOnSentence(is(nullValue(Sentence.class)))
			.withActingRule(is(nullValue(Rule.class)))
			.withAtContext(is(nullValue(Context.class)))
			.withCurrentToken(is(nullValue(Token.class)))
			.withRuleApplies(is(equalTo(false)));
	}
	
	public static class BrillTaggerEventMatcher extends BaseMatcher<BrillTaggerEvent> {
		private Matcher<BrillTagger> sourceMatcher;
		private Matcher<Sentence> onSentenceMatcher;
		private Matcher<Rule> actingRuleMatcher;
		private Matcher<Context> atContextMatcher;
		private Matcher<Token> currentTokenMatcher;
		private Matcher<Boolean> ruleAppliesMatcher;
			
		@Override
		public boolean matches(Object item) {
			if (!(item instanceof BrillTaggerEvent))
				return false;
			
			BrillTaggerEvent other = (BrillTaggerEvent) item;
			
			return 
				sourceMatcher.matches(other.getSource()) &&
				onSentenceMatcher.matches(other.getOnSentence()) &&
				actingRuleMatcher.matches(other.getActingRule()) &&
				atContextMatcher.matches(other.getAtContext()) &&
				currentTokenMatcher.matches(other.getCurrentToken()) &&
				ruleAppliesMatcher.matches(matches(other.doesRuleApplies()));
		}

		@Override
		public void describeTo(Description description) {
			description.appendText(BrillTaggerEvent.class.getName());
			description.appendText("(");
			description.appendText("source ").appendDescriptionOf(sourceMatcher).appendText(", ");
			description.appendText("onSentence ").appendDescriptionOf(onSentenceMatcher).appendText(", ");
			description.appendText("actingRule ").appendDescriptionOf(actingRuleMatcher).appendText(", ");
			description.appendText("atContext ").appendDescriptionOf(atContextMatcher).appendText(", ");
			description.appendText("currentToken ").appendDescriptionOf(currentTokenMatcher).appendText(", ");
			description.appendText("does rule applies? ").appendDescriptionOf(ruleAppliesMatcher);
			description.appendText(")");
		}

		public BrillTaggerEventMatcher withSource(Matcher<BrillTagger> sourceMatcher) {
			this.sourceMatcher = sourceMatcher;
			return this;
		}

		public BrillTaggerEventMatcher withOnSentence(Matcher<Sentence> onSentenceMatcher) {
			this.onSentenceMatcher = onSentenceMatcher;
			return this;
		}

		public BrillTaggerEventMatcher withActingRule(Matcher<Rule> actingRuleMatcher) {
			this.actingRuleMatcher = actingRuleMatcher;
			return this;
		}

		public BrillTaggerEventMatcher withAtContext(Matcher<Context> atContextMatcher) {
			this.atContextMatcher = atContextMatcher;
			return this;
		}

		public BrillTaggerEventMatcher withCurrentToken(Matcher<Token> currentTokenMatcher) {
			this.currentTokenMatcher = currentTokenMatcher;
			return this;
		}

		public BrillTaggerEventMatcher withRuleApplies(Matcher<Boolean> ruleAppliesMatcher) {
			this.ruleAppliesMatcher = ruleAppliesMatcher;
			return this;
		}

		public BrillTaggerEventMatcher from(BrillTagger tagger) {
			return withSource(is(sameInstance(tagger)));
		}

		public BrillTaggerEventMatcher onSentence(Sentence sentence) {
			return withOnSentence(is(sameInstance(sentence)));
		}

		public BrillTaggerEventMatcher underRuleOf(Rule rule) {
			return withActingRule(is(sameInstance(rule)));
		}

		public BrillTaggerEventMatcher atSomeContext() {
			return withAtContext(is(not(nullValue(Context.class))));
		}

		public BrillTaggerEventMatcher inWhichRuleDidntApply() {
			return withRuleApplies(is(equalTo(false)));
		}

	}
}
