package br.eti.rslemos.brill;

import java.util.Collections;
import java.util.List;

import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;

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
			rule.apply(context);
		}
		context.commit();
	}
}

