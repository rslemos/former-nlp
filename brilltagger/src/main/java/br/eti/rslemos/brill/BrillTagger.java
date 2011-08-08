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
package br.eti.rslemos.brill;

import java.util.Collections;
import java.util.List;

import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class BrillTagger implements Tagger {

	private List<Rule> rules;

	public BrillTagger() {
		this(Collections.<Rule>emptyList());
	}

	public BrillTagger(List<Rule> rules) {
		this.rules = rules;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public void tag(Sentence sentence) {
		SentenceContext context = new SentenceContext(sentence);
		
		for (Rule rule : rules)
			applyRule(new DelayedContext(context.clone()), rule);
	}

	static void applyRule(DelayedContext context, Rule rule) {
		while(context.hasNext()) {
			context.next();
			apply(context, rule);
		}
		context.commit();
	}

	private static boolean apply(Context context, Rule rule) {
		if (rule.matches(context)) {
			context.getToken(0).put(Token.POS, rule.getTo());
			return true;
		} else {
			return false;
		}
	}
}

