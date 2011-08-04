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

import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.brill.DelayedContext;
import br.eti.rslemos.tagger.Token;
import br.eti.rslemos.brill.Context;

public abstract privileged aspect BrillTaggerPointcuts {
	
	public pointcut onTagSentence(BrillTagger tagger, Sentence sentence):
		this(tagger) && 
		execution(public void BrillTagger+.tag(Sentence+)) && args(sentence) &&
		within(BrillTagger+);
	
	private pointcut _onRuleApplication(BrillTagger tagger, Rule rule, Sentence sentence, DelayedContext context):
		call(void BrillTagger.applyRule(DelayedContext+, Rule+)) && args(context, rule) &&
		cflow(onTagSentence(tagger, sentence)) && within(BrillTagger+);
		
	public pointcut onRuleApplication(BrillTagger tagger, Rule rule, Sentence sentence):
		_onRuleApplication(tagger, rule, sentence, *);
	
	public pointcut onContextAdvance(BrillTagger tagger, Rule rule, Sentence sentence, Context context):
		call(Token+ Context+.next()) && target(context) &&
		cflow(_onRuleApplication(tagger, rule, sentence, *)) && within(BrillTagger+);
	
	public pointcut onContextCommit(BrillTagger tagger, Rule rule, Sentence sentence, DelayedContext context):
		call(void DelayedContext+.commit()) && target(context) &&
		cflow(_onRuleApplication(tagger, rule, sentence, *)) && within(BrillTagger+);

	public pointcut onContextualRuleApplication(BrillTagger tagger, Rule rule, Sentence sentence, Context context):
		call(boolean BrillTagger.apply(Context+, Rule+)) && args(context, rule) &&
		cflow(_onRuleApplication(tagger, *, sentence, *)) && within(BrillTagger+);
}

