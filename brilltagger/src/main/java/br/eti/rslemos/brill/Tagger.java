package br.eti.rslemos.brill;

import java.util.Collections;
import java.util.List;

public class Tagger {

	private final BaseTagger baseTagger;
	private final List<Rule> rules;

	public Tagger() {
		this(NULL_TAGGER, EMPTY_RULES);
	}

	public Tagger(BaseTagger baseTagger) {
		this(baseTagger, EMPTY_RULES);
	}

	public Tagger(BaseTagger baseTagger, List<Rule> rules) {
		this.baseTagger = baseTagger;
		this.rules = rules;
	}

	public void tagSentence(List<Token> sentence) {
		applyBaseTagger(sentence);

		Token[] tokens = sentence.toArray(new Token[sentence.size()]);
		Context context = new BufferingContext(tokens);
		
		for (Rule rule : rules) {
			applyRule(context, rule);
			context.reset();
		}
	}

	private void applyRule(Context context, Rule rule) {
		while(context.isValidPosition()) {
			rule.apply(context);
			context.advance();
		}
	}

	private void applyBaseTagger(List<Token> sentence) {
		for (Token token : sentence)
			baseTagger.tag(token);
	}

	private static final NullBaseTagger NULL_TAGGER = new NullBaseTagger();
	private static final List<Rule> EMPTY_RULES = Collections.emptyList();

	private static class NullBaseTagger implements BaseTagger {
		public void tag(Token token) {
		}
	}

	private static class BufferingContext extends Context {
		private final Token[] realContents;
		public final String[] tagBuffer;
		public final boolean[] taggedBuffer;

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

