package br.eti.rslemos.brill.events;

import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.mockito.InOrder;
import org.testng.annotations.Test;

import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;

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

	private Sentence<String> anySentence() {
		return (Sentence<String>) anyObject();
	}
}
