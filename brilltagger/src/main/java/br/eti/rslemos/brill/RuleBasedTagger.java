package br.eti.rslemos.brill;

import br.eti.rslemos.tagger.NullTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Tagger;

public class RuleBasedTagger<T> implements Tagger<T> {

	private Tagger<T> baseTagger;
	private Rule<T>[] rules;

	public RuleBasedTagger() {
		this(new NullTagger<T>());
	}

	public RuleBasedTagger(Tagger<T> baseTagger, Rule<T>... rules) {
		this.baseTagger = baseTagger;
		this.rules = rules;
	}

	public Tagger<T> getBaseTagger() {
		return baseTagger;
	}

	public void setBaseTagger(Tagger<T> baseTagger) {
		this.baseTagger = baseTagger;
	}

	public Rule<T>[] getRules() {
		return rules;
	}

	public void setRules(Rule<T>[] rules) {
		this.rules = rules;
	}

	public void tag(Sentence<T> sentence) {
		applyBaseTagger(sentence);

		SentenceContext<T> context = new SentenceContext<T>(sentence);
		
		for (Rule<T> rule : rules)
			applyRule(new DelayedContext<T>(context.clone()), rule);
	}

	private void applyBaseTagger(Sentence<T> sentence) {
		baseTagger.tag(sentence);
	}

	static <T1> void applyRule(DelayedContext<T1> context, Rule<T1> rule) {
		while(context.hasNext()) {
			context.next();
			rule.apply(context);
		}
		context.commit();
	}
}

