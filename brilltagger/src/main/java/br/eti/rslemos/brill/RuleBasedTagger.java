package br.eti.rslemos.brill;

import java.util.Collections;
import java.util.List;

public class RuleBasedTagger implements Tagger {

	private final Tagger baseTagger;
	private final List<Rule> rules;

	public RuleBasedTagger() {
		this(NULL_TAGGER, EMPTY_RULES);
	}

	public RuleBasedTagger(Tagger baseTagger) {
		this(baseTagger, EMPTY_RULES);
	}

	public RuleBasedTagger(Tagger baseTagger, List<Rule> rules) {
		this.baseTagger = baseTagger;
		this.rules = rules;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void tag(Sentence sentence) {
		applyBaseTagger(sentence);

		DelayedContext context = prepareContext(sentence);
		
		for (Rule rule : rules)
			applyRule(context, rule);
	}

	static DelayedContext prepareContext(Sentence sentence) {
		return new DelayedContext(new SentenceContext(sentence));
	}
	
	static void applyRule(DelayedContext context, Rule rule) {
		while(context.hasNext()) {
			context.next();
			rule.apply(context);
		}
		context.commit();
	}

	private void applyBaseTagger(Sentence sentence) {
		baseTagger.tag(sentence);
	}

	public static final Tagger NULL_TAGGER = new NullBaseTagger();
	private static final List<Rule> EMPTY_RULES = Collections.emptyList();

	private static class NullBaseTagger implements Tagger {
		public void tag(Sentence sentence) {
		}
	}

}

