package br.eti.rslemos.brill.events;

import java.util.EventObject;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerEvent extends EventObject implements Cloneable {

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

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}
