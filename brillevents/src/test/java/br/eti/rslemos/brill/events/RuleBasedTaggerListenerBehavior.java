package br.eti.rslemos.brill.events;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.InOrder;
import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.DelayedContext;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

@SuppressWarnings("unchecked")
public class RuleBasedTaggerListenerBehavior {
	
	@Test
	public void shouldAcceptListeners() {
		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>();

		RuleBasedTaggerListener<String> listener = mock(RuleBasedTaggerListener.class);
		
		tagger.addRuleBasedTaggerListener(listener);
		tagger.removeRuleBasedTaggerListener(listener);
	}

	@Test
	public void shouldNotifyTaggingStartAndFinish() {
		RuleBasedTaggerListener<String> listener = mock(RuleBasedTaggerListener.class);
		Sentence<String> sentence = mock(Sentence.class);
		Tagger<String> baseTagger = mock(Tagger.class);

		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>();
		tagger.addRuleBasedTaggerListener(listener);
		tagger.setBaseTagger(baseTagger);
		
		tagger.tag(sentence);
		
		RuleBasedTaggerEvent<String> event = new RuleBasedTaggerEvent<String>(tagger);
		event.setOnSentence(sentence);
		
		InOrder order = inOrder(listener, baseTagger);
		
		order.verify(listener).taggingSentence(event);
		order.verify(baseTagger).tag(anySentence());
		order.verify(listener).sentenceTagged(event);

	}

	@Test
	public void shouldNotifyBaseTaggingStartAndFinish() {
		RuleBasedTaggerListener<String> listener = mock(RuleBasedTaggerListener.class);
		Sentence<String> sentence = mock(Sentence.class);
		Tagger<String> baseTagger = mock(Tagger.class);

		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>();
		tagger.addRuleBasedTaggerListener(listener);
		tagger.setBaseTagger(baseTagger);
		
		tagger.tag(sentence);
		
		RuleBasedTaggerEvent<String> event = new RuleBasedTaggerEvent<String>(tagger);
		event.setOnSentence(sentence);
		
		InOrder order = inOrder(listener, baseTagger);
		
		order.verify(listener).taggingSentence(event);
		order.verify(listener).beforeBaseTagger(event);
		order.verify(baseTagger).tag(anySentence());
		order.verify(listener).afterBaseTagger(event);
		order.verify(listener).sentenceTagged(event);
	}
	
	@Test
	public void shouldNotifyRuleApplicationStartAndFinish() {
		RuleBasedTaggerListener<String> listener = mock(RuleBasedTaggerListener.class);
		Sentence<String> sentence = mock(Sentence.class);
		Tagger<String> baseTagger = mock(Tagger.class);
		Rule<String> rule1 = mock(Rule.class);
		Rule<String> rule2 = mock(Rule.class);
		
		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>();
		tagger.addRuleBasedTaggerListener(listener);
		tagger.setBaseTagger(baseTagger);
		tagger.setRules(Arrays.asList(rule1, rule2));
		
		tagger.tag(sentence);
		
		RuleBasedTaggerEvent<String> event = new RuleBasedTaggerEvent<String>(tagger);
		event.setOnSentence(sentence);
		
		RuleBasedTaggerEvent<String> event1 = (RuleBasedTaggerEvent<String>) event.clone();
		event1.setActingRule(rule1);
		
		RuleBasedTaggerEvent<String> event2 = (RuleBasedTaggerEvent<String>) event.clone();
		event2.setActingRule(rule2);
		
		InOrder order = inOrder(listener, baseTagger, rule1, rule2);
		
		order.verify(listener).taggingSentence(event);
		order.verify(listener).beforeBaseTagger(event);
		order.verify(baseTagger).tag(anySentence());
		order.verify(listener).afterBaseTagger(event);
		order.verify(listener).beforeRuleApplication(event1);
		order.verify(listener).afterRuleApplication(event1);
		order.verify(listener).beforeRuleApplication(event2);
		order.verify(listener).afterRuleApplication(event2);
		order.verify(listener).sentenceTagged(event);
	}

	@Test
	public void shouldNotifyContextAdvanceAndCommit() {
		RuleBasedTaggerListener<String> listener = mock(RuleBasedTaggerListener.class);
		Token<String> token1 = mock(Token.class);
		Token<String> token2 = mock(Token.class);
		Sentence<String> sentence = new DefaultSentence<String>(Arrays.asList(token1, token2));
		
		Tagger<String> baseTagger = mock(Tagger.class);
		Rule<String> rule = mock(Rule.class);
		
		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>();
		tagger.addRuleBasedTaggerListener(listener);
		tagger.setBaseTagger(baseTagger);
		tagger.setRules(Arrays.asList(rule));
		
		tagger.tag(sentence);
		
		RuleBasedTaggerEvent<String> event = new RuleBasedTaggerEvent<String>(tagger);
		event.setOnSentence(sentence);
		
		RuleBasedTaggerEvent<String> eventrule = (RuleBasedTaggerEvent<String>) event.clone();
		eventrule.setActingRule(rule);

		InOrder order = inOrder(listener, baseTagger, rule);
		
		order.verify(listener).taggingSentence(event);
		order.verify(listener).beforeBaseTagger(event);
		order.verify(baseTagger).tag(anySentence());
		order.verify(listener).afterBaseTagger(event);
		order.verify(listener).beforeRuleApplication(eventrule);
		order.verify(listener).advance(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule)), is(not(nullValue(DelayedContext.class))), is(tokenExternallyEquals(token1)), is(equalTo(false)))));
		order.verify(listener).advance(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule)), is(not(nullValue(DelayedContext.class))), is(tokenExternallyEquals(token2)), is(equalTo(false)))));
		order.verify(listener).commit(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule)), is(not(nullValue(DelayedContext.class))), is(nullValue(Token.class)), is(equalTo(false)))));
		order.verify(listener).afterRuleApplication(eventrule);
		order.verify(listener).sentenceTagged(event);
	}

	@Test
	public void shouldNotifyContextualRuleApplication() {
		RuleBasedTaggerListener<String> listener = mock(RuleBasedTaggerListener.class);
		Token<String> token1 = mock(Token.class);
		Token<String> token2 = mock(Token.class);
		Sentence<String> sentence = new DefaultSentence<String>(Arrays.asList(token1, token2));
		
		Tagger<String> baseTagger = mock(Tagger.class);
		Rule<String> rule = mock(Rule.class);
		
		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>();
		tagger.addRuleBasedTaggerListener(listener);
		tagger.setBaseTagger(baseTagger);
		tagger.setRules(Arrays.asList(rule));
		
		tagger.tag(sentence);
		
		RuleBasedTaggerEvent<String> event = new RuleBasedTaggerEvent<String>(tagger);
		event.setOnSentence(sentence);
		event.setActingRule(rule);
		
		InOrder order = inOrder(listener, rule);
		
		order.verify(listener).advance(anyEvent());
		order.verify(rule).apply(anyContext());
		order.verify(listener).ruleApplied(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule)), is(not(nullValue(DelayedContext.class))), is(nullValue(Token.class)), is(equalTo(false)))));
		order.verify(listener).advance(anyEvent());
		order.verify(rule).apply(anyContext());
		order.verify(listener).ruleApplied(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule)), is(not(nullValue(DelayedContext.class))), is(nullValue(Token.class)), is(equalTo(false)))));
		order.verify(listener).commit(anyEvent());
		order.verify(listener, never()).ruleApplied(anyEvent());
		order.verify(rule, never()).apply(anyContext());
	}

	private static Matcher<Token> tokenExternallyEquals(Token token) {
		return new CustomTokenMatcher(token);
	}
	
	private static class CustomTokenMatcher extends BaseMatcher<Token> {

		private final Token wanted;

		public CustomTokenMatcher(Token wanted) {
			this.wanted = wanted;
		}

		@Override
		public boolean matches(Object item) {
			if (!(item instanceof Token))
				return false;
			
			Token other = (Token) item;
			
			String wantedWord = wanted.getWord();
			Object wantedTag = wanted.getTag();
			String actualWord = other.getWord();
			Object actualTag = other.getTag();
			
			return
				(wantedWord != null ? wantedWord.equals(actualWord) : actualWord == null) &&
				(wantedTag != null ? wantedTag.equals(actualTag) : actualTag == null);
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("externally like ");
			
			String wantedWord = wanted.getWord();
			if (wantedWord != null)
				description.appendText("\"");
			description.appendText(wantedWord);
			if (wantedWord != null)
				description.appendText("\"");
			description.appendText("/");
			description.appendValue(wanted.getTag());
		}
		
	}

	private static Matcher<RuleBasedTaggerEvent> matchesEvent(
			Matcher<RuleBasedTagger<String>> taggerMatcher,
			Matcher<Sentence<String>> sentenceMatcher,
			Matcher<Rule<String>> ruleMatcher,
			Matcher<DelayedContext> contextMatcher,
			Matcher<Token> tokenMatcher,
			Matcher<Boolean> ruleAppliesMatcher) {

		return new CustomEventMatcher(taggerMatcher, sentenceMatcher, ruleMatcher, contextMatcher, tokenMatcher, ruleAppliesMatcher);
	}
	
	private static class CustomEventMatcher extends BaseMatcher<RuleBasedTaggerEvent> {

		private final Matcher<RuleBasedTagger<String>> sourceMatcher;
		private final Matcher<Sentence<String>> onSentenceMatcher;
		private final Matcher<Rule<String>> actingRuleMatcher;
		private final Matcher<DelayedContext> contextMatcher;
		private final Matcher<Token> tokenMatcher;
		private final Matcher<Boolean> ruleAppliesMatcher;

		public CustomEventMatcher(
				Matcher<RuleBasedTagger<String>> sourceMatcher,
				Matcher<Sentence<String>> onSentenceMatcher,
				Matcher<Rule<String>> actingRuleMatcher,
				Matcher<DelayedContext> contextMatcher,
				Matcher<Token> tokenMatcher,
				Matcher<Boolean> ruleAppliesMatcher) {
					this.sourceMatcher = sourceMatcher;
					this.onSentenceMatcher = onSentenceMatcher;
					this.actingRuleMatcher = actingRuleMatcher;
					this.contextMatcher = contextMatcher;
					this.tokenMatcher = tokenMatcher;
					this.ruleAppliesMatcher = ruleAppliesMatcher;
		}

		@Override
		public boolean matches(Object item) {
			if (!(item instanceof RuleBasedTaggerEvent))
				return false;
			
			RuleBasedTaggerEvent other = (RuleBasedTaggerEvent) item;
			
			return 
				sourceMatcher.matches(other.getSource()) &&
				onSentenceMatcher.matches(other.getOnSentence()) &&
				actingRuleMatcher.matches(other.getActingRule()) &&
				contextMatcher.matches(other.getContext()) &&
				tokenMatcher.matches(other.getToken()) &&
				ruleAppliesMatcher.matches(matches(other.doesRuleApplies()));
		}

		@Override
		public void describeTo(Description description) {
			description.appendText(RuleBasedTaggerEvent.class.getName());
			description.appendText("(");
			description.appendText("source ").appendDescriptionOf(sourceMatcher).appendText(", ");
			description.appendText("onSentence ").appendDescriptionOf(onSentenceMatcher).appendText(", ");
			description.appendText("actingRule ").appendDescriptionOf(actingRuleMatcher).appendText(", ");
			description.appendText("context ").appendDescriptionOf(contextMatcher).appendText(", ");
			description.appendText("token ").appendDescriptionOf(tokenMatcher).appendText(", ");
			description.appendText("does rule applies? ").appendDescriptionOf(ruleAppliesMatcher);
			description.appendText(")");
		}
		
	}

	private static Sentence<String> anySentence() {
		return (Sentence<String>) anyObject();
	}

	private static RuleBasedTaggerEvent<String> anyEvent() {
		return (RuleBasedTaggerEvent<String>) anyObject();
	}

	private static Context<String> anyContext() {
		return (Context<String>) anyObject();
	}
}
