/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.brill.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.DelayedContext;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public aspect BrillTaggerObserver extends BrillTaggerPointcuts {
	private List<BrillTaggerListener> BrillTagger.listeners = new ArrayList<BrillTaggerListener>();
	
	public void BrillTagger.addBrillTaggerListener(BrillTaggerListener listener) {
		listeners.add(listener);
	}
	
	public void BrillTagger.removeBrillTaggerListener(BrillTaggerListener listener) {
		listeners.remove(listener);
	}

	private void BrillTagger.fireNotification(Method method, BrillTaggerEvent prototype) {
		ObserverUtils.fireNotification(listeners, method, prototype);
	}

	private static final Method TAGGING_START;
	private static final Method TAGGING_FINISH;
	private static final Method RULE_APPLICATION_START;
	private static final Method RULE_APPLICATION_FINISH;
	private static final Method CONTEXT_ADVANCED;
	private static final Method CONTEXT_COMMITED;
	private static final Method RULE_APPLIED;
	
	static {
		Class<BrillTaggerListener> clazz = BrillTaggerListener.class;
		Class<?>[] args = new Class[] {BrillTaggerEvent.class};
		
		try {
			TAGGING_START = clazz.getMethod("taggingStart", args);
			TAGGING_FINISH = clazz.getMethod("taggingFinish", args);
			RULE_APPLICATION_START = clazz.getMethod("ruleApplicationStart", args);
			RULE_APPLICATION_FINISH = clazz.getMethod("ruleApplicationFinish", args);
			CONTEXT_ADVANCED = clazz.getMethod("contextAdvanced", args);
			CONTEXT_COMMITED = clazz.getMethod("contextCommitted", args);
			RULE_APPLIED = clazz.getMethod("ruleApplied", args);
		} catch (Exception e) {
			throw (Error)(new LinkageError().initCause(e));
		}
	}
	
	before(BrillTagger tagger, Sentence sentence): onTagSentence(tagger, sentence) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		
		tagger.fireNotification(TAGGING_START, prototype);
	}

	after(BrillTagger tagger, Sentence sentence) returning: onTagSentence(tagger, sentence) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
	
		tagger.fireNotification(TAGGING_FINISH, prototype);
	}

	before(BrillTagger tagger, Rule rule, Sentence sentence): onRuleApplication(tagger, rule, sentence) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);

		tagger.fireNotification(RULE_APPLICATION_START, prototype);
	}

	after(BrillTagger tagger, Rule rule, Sentence sentence) returning: onRuleApplication(tagger, rule, sentence) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);

		tagger.fireNotification(RULE_APPLICATION_FINISH, prototype);
	}

	after(BrillTagger tagger, Rule rule, Sentence sentence, Context context) returning (Token token): 
		onContextAdvance(tagger, rule, sentence, context) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);
		prototype.setAtContext(context);
		prototype.setCurrentToken(token);

		tagger.fireNotification(CONTEXT_ADVANCED, prototype);
	}
	
	after(BrillTagger tagger, Rule rule, Sentence sentence, DelayedContext context) returning: 
		onContextCommit(tagger, rule, sentence, context) {
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);
		prototype.setAtContext(context);

		tagger.fireNotification(CONTEXT_COMMITED, prototype);
	}

	after(BrillTagger tagger, Rule rule, Sentence sentence, Context context) returning (boolean result):
		onContextualRuleApplication(tagger, rule, sentence, context) {
		
		BrillTaggerEvent prototype = new BrillTaggerEvent(tagger);
		prototype.setOnSentence(sentence);
		prototype.setActingRule(rule);
		prototype.setAtContext(context);
		prototype.setRuleApplies(result);

		tagger.fireNotification(RULE_APPLIED, prototype);
	}
}
