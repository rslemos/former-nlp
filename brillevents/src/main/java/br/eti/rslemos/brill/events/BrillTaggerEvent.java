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

import java.util.EventObject;

import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private Sentence onSentence;
	private Rule actingRule;
	private Context atContext;
	private Token currentToken;

	private boolean applies;

	public BrillTaggerEvent(BrillTagger tagger) {
		super(tagger);
	}

	public void setOnSentence(Sentence sentence) {
		this.onSentence = sentence;
	}

	public Sentence getOnSentence() {
		return onSentence;
	}

	public void setActingRule(Rule rule) {
		this.actingRule = rule;
	}
	
	public Rule getActingRule() {
		return actingRule;
	}

	public void setAtContext(Context atContext) {
		this.atContext = atContext;
	}
	
	public Context getAtContext() {
		return atContext;
	}

	public void setCurrentToken(Token currentToken) {
		this.currentToken = currentToken;
	}
	
	public Token getCurrentToken() {
		return currentToken;
	}

	public void setRuleApplies(boolean applies) {
		this.applies = applies;
	}
	
	public boolean doesRuleApplies() {
		return applies;
	}

    public String toString() {
        return "BrillTaggerEvent[" + paramString() + "] from " + source;
    }

	private String paramString() {
		StringBuilder result = new StringBuilder();
		
		result.append("onSentence=");
		result.append(String.valueOf(onSentence));
		result.append(',');
		
		result.append("actingRule=");
		result.append(String.valueOf(actingRule));
		result.append(',');
		
		result.append("atContext=");
		result.append(String.valueOf(atContext));
		result.append(',');
		
		result.append("currentToken=");
		result.append(String.valueOf(currentToken));
		result.append(',');
		
		result.append("applies=");
		result.append(String.valueOf(applies));
		
		return result.toString();
	}
}
