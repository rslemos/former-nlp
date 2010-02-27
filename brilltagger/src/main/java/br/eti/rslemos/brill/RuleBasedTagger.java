package br.eti.rslemos.brill;

import br.eti.rslemos.tagger.NullTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;

public class RuleBasedTagger implements Tagger {

	private Tagger baseTagger;
	private Rule[] rules;

	public RuleBasedTagger() {
		this(NullTagger.NULL_TAGGER);
	}

	public RuleBasedTagger(Tagger baseTagger, Rule... rules) {
		this.baseTagger = baseTagger;
		this.rules = rules;
	}

	public Tagger getBaseTagger() {
		return baseTagger;
	}

	public void setBaseTagger(Tagger baseTagger) {
		this.baseTagger = baseTagger;
	}

	public Rule[] getRules() {
		return rules;
	}

	public void setRules(Rule[] rules) {
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

