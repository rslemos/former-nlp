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
	private Context context;
	private Token token;

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

	public void setContext(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return context;
	}

	public void setToken(Token token) {
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}

	public void setRuleApplies(boolean applies) {
		this.applies = applies;
	}
	
	public boolean doesRuleApplies() {
		return applies;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null)
			return false;

		if (this.getClass() != o.getClass())
			return false;

		BrillTaggerEvent other = (BrillTaggerEvent)o;
		
		return
			(source != null ? source.equals(other.source) : other.source == null) &&
			(onSentence != null ? onSentence.equals(other.onSentence) : other.onSentence == null) &&
			(actingRule != null ? actingRule.equals(other.actingRule) : other.actingRule == null) &&
			(context != null ? context.equals(other.context) : other.context == null) &&
			(token != null ? token.equals(other.token) : other.token == null);
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		
		hashCode += source != null ? source.hashCode() : 0;
		hashCode *= 3;
		hashCode += onSentence != null ? onSentence.hashCode() : 0;
		hashCode *= 5;
		hashCode += actingRule != null ? actingRule.hashCode() : 0;
		hashCode *= 7;
		hashCode += context != null ? context.hashCode() : 0;
		hashCode *= 11;
		hashCode += token != null ? token.hashCode() : 0;
		
		return hashCode;
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
