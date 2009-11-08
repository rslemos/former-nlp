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
		return new DelayedContext(sentence);
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

	public static class DelayedContext extends SentenceContext {
		private final Sentence realContents;
		private final String[] tagBuffer;
		private final boolean[] taggedBuffer;

		private DelayedContext(Sentence contents) {
			super(null);
			realContents = contents;
			
			tagBuffer = new String[contents.size()];
			taggedBuffer = new boolean[contents.size()];
			BufferingToken[] bufferingContents = new BufferingToken[contents.size()];
			for(int i = 0; i < contents.size(); i++)
				bufferingContents[i] = new BufferingToken(i);
			
			super.setContents(new DefaultSentence(bufferingContents));
		}

		public void commit() {
			for (int i = 0; i < taggedBuffer.length; i++) {
				if (taggedBuffer[i]) {
					taggedBuffer[i] = false;
					realContents.get(i).setTag(tagBuffer[i]);
				}
			}
			
			pointer = -1;
		}

		private class BufferingToken implements Token {
			private final int i;
			
			protected BufferingToken(int i) {
				this.i = i;
			}

			public String getTag() {
				return realContents.get(i).getTag();
			}

			public String getWord() {
				return realContents.get(i).getWord();
			}

			public void setTag(String tag) {
				tagBuffer[i] = tag;
				taggedBuffer[i] = true;
			}
			
		}
	}

}

