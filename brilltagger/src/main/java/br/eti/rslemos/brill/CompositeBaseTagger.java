package br.eti.rslemos.brill;

import java.util.List;

public class CompositeBaseTagger implements BaseTagger {

	private final List<BaseTagger> taggers;

	public CompositeBaseTagger(List<BaseTagger> taggers) {
		this.taggers = taggers;
	}

	public void tag(final Token token) {
		final boolean[] stop = new boolean[1];
		
		Token wrappedToken = new Token() {
			public String getTag() {
				return token.getTag();
			}

			public String getWord() {
				return token.getWord();
			}

			public void setTag(String tag) {
				token.setTag(tag);
				stop[0] = true;
			}
		};
		
		for (BaseTagger tagger : taggers) {
			tagger.tag(wrappedToken);
			if (stop[0])
				break;
		}
	}

}
