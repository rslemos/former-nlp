package br.eti.rslemos.brill;

import java.util.Collections;
import java.util.List;

import br.eti.rslemos.tagger.NullTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;

public class BrillTagger implements Tagger {

	private Tagger baseTagger;
	private List<Rule> rules;

	public BrillTagger() {
		this(new NullTagger());
	}

	public BrillTagger(Tagger baseTagger) {
		this(baseTagger, Collections.<Rule>emptyList());
	}

	public BrillTagger(Tagger baseTagger, List<Rule> rules) {
		this.baseTagger = baseTagger;
		this.rules = rules;
	}

	public Tagger getBaseTagger() {
		return baseTagger;
	}

	public void setBaseTagger(Tagger baseTagger) {
		this.baseTagger = baseTagger;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public void tag(Sentence sentence) {
		applyBaseTagger(sentence);

		SentenceContext context = new SentenceContext(sentence);
		
		for (Rule rule : rules)
			applyRule(new DelayedContext(context.clone()), rule);
	}

	private void applyBaseTagger(Sentence sentence) {
		baseTagger.tag(sentence);
	}

	static void applyRule(DelayedContext context, Rule rule) {
		while(context.hasNext()) {
			context.next();
			rule.apply(context);
		}
		context.commit();
	}
}

