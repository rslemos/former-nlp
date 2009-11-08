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

	public static class DelayedContext implements Context {
		private final Sentence contents;
		private final SentenceContext context;

		private final String[] tagBuffer;
		private final boolean[] taggedBuffer;

		private DelayedContext(Sentence contents) {
			this.contents = contents;
			context = new SentenceContext(contents);
			
			tagBuffer = new String[contents.size()];
			taggedBuffer = new boolean[contents.size()];
		}

		public void commit() {
			for (int i = 0; i < taggedBuffer.length; i++) {
				if (taggedBuffer[i]) {
					contents.get(i).setTag(tagBuffer[i]);
				}
			}
			
			context.pointer = -1;
		}

		public Token getToken(int offset) {
			return delayedToken(context.getToken(offset), offset);
		}

		public boolean hasNext() {
			return context.hasNext();
		}

		public Token next() {
			return delayedToken(context.next(), -1);
		}

		public void remove() {
			context.remove();
		}
		
		@Override
		public DelayedContext clone() {
			try {
				return (DelayedContext) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new Error("Object#clone() threw CloneNotSupportedException", e);
			}
		}

		private Token delayedToken(final Token token, final int offset) {
			return new Token() {
				private final int i = context.pointer + offset;
				public String getTag() {
					return token.getTag();
				}

				public String getWord() {
					return token.getWord();
				}

				public void setTag(String tag) {
					tagBuffer[i] = tag;
					taggedBuffer[i] = true;
				}
				
			};
		}
	}

}

