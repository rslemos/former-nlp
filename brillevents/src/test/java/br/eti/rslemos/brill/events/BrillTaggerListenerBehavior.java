package br.eti.rslemos.brill.events;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;
import static br.eti.rslemos.brill.events.BrillCustomMatchers.*;
import static br.eti.rslemos.brill.events.BrillTaggerCustomMatchers.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

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
	public void shouldNotifyTaggingStartAndFinish() {
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener, baseTagger);
		
		order.verify(listener).taggingStart(eventWithSentence());
		order.verify(baseTagger).tag(anySentence());
		order.verify(listener).taggingFinish(eventWithSentence());
	}

	@Test
	public void shouldNotifyBaseTaggingStartAndFinish() {
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener, baseTagger);
		
		order.verify(listener).taggingStart(anyEvent());
		
		order.verify(listener).baseTaggingStart(eventWithSentence());
		order.verify(baseTagger).tag(anySentence());
		order.verify(listener).baseTaggingFinish(eventWithSentence());
		
		order.verify(listener).taggingFinish(anyEvent());
	}

	@Test
	public void shouldNotifyRuleApplicationStartAndFinish() {
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).baseTaggingFinish(anyEvent());
		
		order.verify(listener).ruleApplicationStart(eventWithSentenceAndRule(rule1));
		order.verify(listener).ruleApplicationFinish(eventWithSentenceAndRule(rule1));
		order.verify(listener).ruleApplicationStart(eventWithSentenceAndRule(rule2));
		order.verify(listener).ruleApplicationFinish(eventWithSentenceAndRule(rule2));
		
		order.verify(listener).taggingFinish(anyEvent());
	}

	@Test
	public void shouldNotifyContextAdvanceAndCommit() {
		// override ruleset
		tagger.setRules(Arrays.asList(rule1));
		
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).ruleApplicationStart(anyEvent());
		
		order.verify(listener).contextAdvanced(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule1)), is(not(nullValue(Context.class))), is(tokenExternallyEquals(token1)), is(equalTo(false)))));
		order.verify(listener).contextAdvanced(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule1)), is(not(nullValue(Context.class))), is(tokenExternallyEquals(token2)), is(equalTo(false)))));
		order.verify(listener).contextCommitted(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule1)), is(not(nullValue(Context.class))), is(nullValue(Token.class)), is(equalTo(false)))));

		order.verify(listener).ruleApplicationFinish(anyEvent());
	}

	@Test
	public void shouldNotifyContextualRuleApplication() {
		// override ruleset
		tagger.setRules(Arrays.asList(rule1));
		
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener, rule1);
		
		order.verify(listener).contextAdvanced(anyEvent());
		order.verify(rule1).apply(anyContext());
		order.verify(listener).ruleApplied(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule1)), is(not(nullValue(Context.class))), is(nullValue(Token.class)), is(equalTo(false)))));

		order.verify(listener).contextAdvanced(anyEvent());
		order.verify(rule1).apply(anyContext());
		order.verify(listener).ruleApplied(argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule1)), is(not(nullValue(Context.class))), is(nullValue(Token.class)), is(equalTo(false)))));
		
		order.verify(listener).contextCommitted(anyEvent());
		
		order.verify(listener, never()).ruleApplied(anyEvent());
		order.verify(rule1, never()).apply(anyContext());
	}

	@Test
	public void example() {
		listener = (BrillTaggerListener) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] {BrillTaggerListener.class}, new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.printf("%s\n\t%s\n", method.getName(), args[0]);
				
				return null;
			}
			
		});
		
		tagger.addBrillTaggerListener(listener);
		
		tagger.tag(sentence);
	}
	
	private BrillTaggerEvent eventWithSentence() {
		return argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(nullValue(Rule.class)), is(nullValue(Context.class)), is(nullValue(Token.class)), is(equalTo(false))));
	}
	
	private BrillTaggerEvent eventWithSentenceAndRule(Rule rule) {
		return argThat(matchesEvent(is(sameInstance(tagger)), is(sameInstance(sentence)), is(sameInstance(rule)), is(nullValue(Context.class)), is(nullValue(Token.class)), is(equalTo(false))));
	}
}
