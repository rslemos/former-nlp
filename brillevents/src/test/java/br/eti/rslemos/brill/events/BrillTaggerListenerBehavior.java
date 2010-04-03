package br.eti.rslemos.brill.events;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

@SuppressWarnings("unchecked")
public class BrillTaggerListenerBehavior {
	@Mock private BrillTaggerListener listener;

	private BrillTagger tagger;;

	@Mock private Tagger baseTagger;

	@Mock private Rule rule1;
	@Mock private Rule rule2;

	@Mock private Token token1;
	@Mock private Token token2;
	private Sentence sentence;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		tagger = new BrillTagger();
		tagger.addBrillTaggerListener(listener);
		
		tagger.setBaseTagger(baseTagger);
		tagger.setRules(Arrays.asList(rule1, rule2));

		sentence = new DefaultSentence(Arrays.asList(token1, token2));
	}
	
	@Test
	public void shouldAcceptListeners() {
		//tagger.addBrillTaggerListener(listener);
		tagger.removeBrillTaggerListener(listener);
	}

	@Test
	public void shouldNotifyObjectgingStartAndFinish() {
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener, baseTagger);
		
		order.verify(listener).taggingSentence(eventWithSentence());
		order.verify(baseTagger).tag(anySentence());
		order.verify(listener).sentenceObjectged(eventWithSentence());
	}

	@Test
	public void shouldNotifyBaseObjectgingStartAndFinish() {
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener, baseTagger);
		
		order.verify(listener).taggingSentence(anyEvent());
		
		order.verify(listener).beforeBaseTagger(eventWithSentence());
		order.verify(baseTagger).tag(anySentence());
		order.verify(listener).afterBaseTagger(eventWithSentence());
		
		order.verify(listener).sentenceObjectged(anyEvent());
	}

	@Test
	public void shouldNotifyRuleApplicationStartAndFinish() {
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).afterBaseTagger(anyEvent());
		
		order.verify(listener).beforeRuleApplication(eventWithSentenceAndRule(rule1));
		order.verify(listener).afterRuleApplication(eventWithSentenceAndRule(rule1));
		order.verify(listener).beforeRuleApplication(eventWithSentenceAndRule(rule2));
		order.verify(listener).afterRuleApplication(eventWithSentenceAndRule(rule2));
		
		order.verify(listener).sentenceObjectged(anyEvent());
	}

	@Test
	public void shouldNotifyContextAdvanceAndCommit() {
		// override ruleset
		tagger.setRules(Arrays.asList(rule1));
		
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).beforeRuleApplication(anyEvent());
		
		order.verify(listener).advance(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule1)), is(not(nullValue(Context.class))), is(tokenExternallyEquals(token1)), is(equalTo(false)))));
		order.verify(listener).advance(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule1)), is(not(nullValue(Context.class))), is(tokenExternallyEquals(token2)), is(equalTo(false)))));
		order.verify(listener).commit(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule1)), is(not(nullValue(Context.class))), is(nullValue(Token.class)), is(equalTo(false)))));

		order.verify(listener).afterRuleApplication(anyEvent());
	}

	@Test
	public void shouldNotifyContextualRuleApplication() {
		// override ruleset
		tagger.setRules(Arrays.asList(rule1));
		
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener, rule1);
		
		order.verify(listener).advance(anyEvent());
		order.verify(rule1).apply(anyContext());
		order.verify(listener).ruleApplied(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule1)), is(not(nullValue(Context.class))), is(nullValue(Token.class)), is(equalTo(false)))));

		order.verify(listener).advance(anyEvent());
		order.verify(rule1).apply(anyContext());
		order.verify(listener).ruleApplied(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule1)), is(not(nullValue(Context.class))), is(nullValue(Token.class)), is(equalTo(false)))));
		
		order.verify(listener).commit(anyEvent());
		
		order.verify(listener, never()).ruleApplied(anyEvent());
		order.verify(rule1, never()).apply(anyContext());
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
			Object wantedObject = wanted.getTag();
			String actualWord = other.getWord();
			Object actualObject = other.getTag();
			
			return
				(wantedWord != null ? wantedWord.equals(actualWord) : actualWord == null) &&
				(wantedObject != null ? wantedObject.equals(actualObject) : actualObject == null);
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

	private static Matcher<BrillTaggerEvent> matchesEvent(
			Matcher<BrillTagger> taggerMatcher,
			Matcher<Sentence> sentenceMatcher,
			Matcher<Rule> ruleMatcher,
			Matcher<Context> contextMatcher,
			Matcher<Token> tokenMatcher,
			Matcher<Boolean> ruleAppliesMatcher) {

		return new CustomEventMatcher(taggerMatcher, sentenceMatcher, ruleMatcher, contextMatcher, tokenMatcher, ruleAppliesMatcher);
	}
	
	private static class CustomEventMatcher extends BaseMatcher<BrillTaggerEvent> {

		private final Matcher<BrillTagger> sourceMatcher;
		private final Matcher<Sentence> onSentenceMatcher;
		private final Matcher<Rule> actingRuleMatcher;
		private final Matcher<Context> contextMatcher;
		private final Matcher<Token> tokenMatcher;
		private final Matcher<Boolean> ruleAppliesMatcher;

		public CustomEventMatcher(
				Matcher<BrillTagger> sourceMatcher,
				Matcher<Sentence> onSentenceMatcher,
				Matcher<Rule> actingRuleMatcher,
				Matcher<Context> contextMatcher,
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
			if (!(item instanceof BrillTaggerEvent))
				return false;
			
			BrillTaggerEvent other = (BrillTaggerEvent) item;
			
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
			description.appendText(BrillTaggerEvent.class.getName());
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

	private static Sentence anySentence() {
		return anyObject();
	}

	private static BrillTaggerEvent anyEvent() {
		return anyObject();
	}

	private static Context anyContext() {
		return anyObject();
	}

	private BrillTaggerEvent eventWithSentence() {
		return argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(nullValue(Rule.class)), is(nullValue(Context.class)), is(nullValue(Token.class)), is(equalTo(false))));
	}
	
	private BrillTaggerEvent eventWithSentenceAndRule(Rule rule) {
		return argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule)), is(nullValue(Context.class)), is(nullValue(Token.class)), is(equalTo(false))));
	}
}
