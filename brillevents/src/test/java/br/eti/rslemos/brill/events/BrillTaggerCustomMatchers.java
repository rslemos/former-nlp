package br.eti.rslemos.brill.events;

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

	public static Matcher<BrillTaggerEvent> matchesEvent(
			final Matcher<BrillTagger> sourceMatcher,
			final Matcher<Sentence> onSentenceMatcher,
			final Matcher<Rule> actingRuleMatcher,
			final Matcher<Context> atContextMatcher,
			final Matcher<Token> currentTokenMatcher,
			final Matcher<Boolean> ruleAppliesMatcher) {

		return new BaseMatcher<BrillTaggerEvent>() {
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
		};
	}
}
