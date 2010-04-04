package br.eti.rslemos.brill.events;

import static br.eti.rslemos.brill.events.BrillCustomMatchers.*;
import static br.eti.rslemos.brill.events.BrillTaggerCustomMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.events.BrillTaggerCustomMatchers.BrillTaggerEventMatcher;
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
		
		order.verify(listener).taggingStart(argThat(isBasicBrillTaggerEvent()));
		order.verify(baseTagger).tag(anySentence());
		order.verify(listener).taggingFinish(argThat(isBasicBrillTaggerEvent()));
	}

	@Test
	public void shouldNotifyBaseTaggingStartAndFinish() {
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener, baseTagger);
		
		order.verify(listener).taggingStart(anyEvent());
		
		order.verify(listener).baseTaggingStart(argThat(isBasicBrillTaggerEvent()));
		order.verify(baseTagger).tag(anySentence());
		order.verify(listener).baseTaggingFinish(argThat(isBasicBrillTaggerEvent()));
		
		order.verify(listener).taggingFinish(anyEvent());
	}

	@Test
	public void shouldNotifyRuleApplicationStartAndFinish() {
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).baseTaggingFinish(anyEvent());
		
		order.verify(listener).ruleApplicationStart(argThat(isBasicBrillTaggerEvent().underRuleOf(rule1)));
		order.verify(listener).ruleApplicationFinish(argThat(isBasicBrillTaggerEvent().underRuleOf(rule1)));
		
		order.verify(listener).ruleApplicationStart(argThat(isBasicBrillTaggerEvent().underRuleOf(rule2)));
		order.verify(listener).ruleApplicationFinish(argThat(isBasicBrillTaggerEvent().underRuleOf(rule2)));
		
		order.verify(listener).taggingFinish(anyEvent());
	}

	@Test
	public void shouldNotifyContextAdvanceAndCommit() {
		// override ruleset
		tagger.setRules(Arrays.asList(rule1));
		
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).ruleApplicationStart(anyEvent());
		
		order.verify(listener).contextAdvanced(argThat(
				isBasicBrillTaggerEvent()
					.underRuleOf(rule1)
					.atSomeContext()
					.withCurrentToken(is(tokenExternallyEquals(token1)))
					.inWhichRuleDidntApply()));
		
		order.verify(listener).contextAdvanced(argThat(
				isBasicBrillTaggerEvent()
					.underRuleOf(rule1)
					.atSomeContext()
					.withCurrentToken(is(tokenExternallyEquals(token2)))
					.inWhichRuleDidntApply()));
		
		order.verify(listener).contextCommitted(argThat(
				isBasicBrillTaggerEvent()
					.underRuleOf(rule1)
					.atSomeContext()));

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
		order.verify(listener).ruleApplied(argThat(
				isBasicBrillTaggerEvent()
					.underRuleOf(rule1)
					.atSomeContext()));

		order.verify(listener).contextAdvanced(anyEvent());
		order.verify(rule1).apply(anyContext());
		order.verify(listener).ruleApplied(argThat(
				isBasicBrillTaggerEvent()
					.underRuleOf(rule1)
					.atSomeContext()));
		
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

	private BrillTaggerEventMatcher isBasicBrillTaggerEvent() {
		return isBrillTaggerEvent().from(tagger).onSentence(sentence);
	}
}
