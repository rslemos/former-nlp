package br.eti.rslemos.brill.events;

import static org.mockito.Mockito.*;

import java.beans.PropertyChangeEvent;
import java.util.Collections;
import java.util.List;

import org.mockito.InOrder;
import org.testng.annotations.Test;

import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.RuleBasedTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;

public class RuleBasedTaggerListenerBehavior {
	
	@Test
	public void shouldAcceptListeners() {
		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>();

		RuleBasedTaggerListener<String> listener = mock(RuleBasedTaggerListener.class);
		
		tagger.addRuleBasedTaggerListener(listener);
		tagger.removeRuleBasedTaggerListener(listener);
	}

	@Test
	public void shouldNotifyTaggingStartAndFinishing() {
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
		order.verify(baseTagger).tag((Sentence<String>) anyObject());
		order.verify(listener).sentenceTagged(event);

	}
}
