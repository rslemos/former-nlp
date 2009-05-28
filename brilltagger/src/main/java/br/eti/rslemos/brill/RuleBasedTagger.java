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

	public void tagSentence(List<Token> sentence) {
		applyBaseTagger(sentence);

		BufferingContext context = prepareContext(sentence);
		
		for (Rule rule : rules)
			applyRule(context, rule);
	}

	static BufferingContext prepareContext(List<Token> sentence) {
		Token[] tokens = sentence.toArray(new Token[sentence.size()]);
		return prepareContext(tokens);
	}

	static BufferingContext prepareContext(Token[] tokens) {
		return new BufferingContext(tokens);
	}
	
	static void applyRule(BufferingContext context, Rule rule) {
		while(context.isValidPosition()) {
			rule.apply(context);
			context.advance();
		}
		context.reset();
	}

	private void applyBaseTagger(List<Token> sentence) {
		baseTagger.tagSentence(sentence);
	}

	private static final Tagger NULL_TAGGER = new NullBaseTagger();
	private static final List<Rule> EMPTY_RULES = Collections.emptyList();

	private static class NullBaseTagger implements Tagger {
		public void tagSentence(List<Token> sentence) {
		}
	}

	static final class BufferingContext extends ArrayContext {
		private final Token[] realContents;
		private final String[] tagBuffer;
		private final boolean[] taggedBuffer;

		private BufferingContext(Token[] contents) {
			super(null);
			realContents = contents;
			
			tagBuffer = new String[contents.length];
			taggedBuffer = new boolean[contents.length];
			BufferingToken[] bufferingContents = new BufferingToken[contents.length];
			for(int i = 0; i < contents.length; i++)
				bufferingContents[i] = new BufferingToken(i);
			
			super.setContents(bufferingContents);
		}

		@Override
		public void reset() {
			for (int i = 0; i < taggedBuffer.length; i++) {
				if (taggedBuffer[i]) {
					taggedBuffer[i] = false;
					realContents[i].setTag(tagBuffer[i]);
				}
			}
			super.reset();
		}

		private class BufferingToken implements Token {
			private final int i;
			
			protected BufferingToken(int i) {
				this.i = i;
			}

			public String getTag() {
				return realContents[i].getTag();
			}

			public String getWord() {
				return realContents[i].getWord();
			}

			public void setTag(String tag) {
				tagBuffer[i] = tag;
				taggedBuffer[i] = true;
			}
			
		}
	}

}

