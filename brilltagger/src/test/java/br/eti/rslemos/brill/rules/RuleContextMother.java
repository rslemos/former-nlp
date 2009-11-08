package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.SentenceContext;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Token;

public abstract class RuleContextMother {

	public static final String ALT = "alt-";
	
	public static final String TO_TAG = "to-tag";

	public static final String PREV4_WORD = "prev4-word4";
	public static final String PREV4_TAG = "prev4-tag4";

	public static final String PREV3_WORD = "prev3-word3";
	public static final String PREV3_TAG = "prev3-tag3";

	public static final String PREV2_WORD = "prev2-word2";
	public static final String PREV2_TAG = "prev2-tag2";

	public static final String PREV1_WORD = "prev1-word1";
	public static final String PREV1_TAG = "prev1-tag1";

	public static final String THIS_WORD = "this-word";
	public static final String THIS_TAG = "this-tag";
	
	public static final String NEXT1_WORD = "next1-word1";
	public static final String NEXT1_TAG = "next1-tag1";

	public static final String NEXT2_WORD = "next2-word2";
	public static final String NEXT2_TAG = "next2-tag2";

	public static final String NEXT3_WORD = "next3-word3";
	public static final String NEXT3_TAG = "next3-tag3";

	public static final String NEXT4_WORD = "next4-word4";
	public static final String NEXT4_TAG = "next4-tag4";

	public static Context buildContext() {
		return buildContext(5, RuleContextMother.getStandardTokens());
	}

	public static Context buildAltContext() {
		return buildContext(5, getAltTokens());
	}

	public static Context buildUntaggedContext() {
		return buildContext(5, RuleContextMother.getUntaggedTokens());
	}

	private static Token mockToken(String word, String tag) {
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn(word);
		when(token.getTag() ).thenReturn(tag);
		
		return token;
	}

	public static Token[] getStandardTokens() {
		return new Token[] {
				mockToken(PREV4_WORD, PREV4_TAG),
				mockToken(PREV3_WORD, PREV3_TAG),
				mockToken(PREV2_WORD, PREV2_TAG),
				mockToken(PREV1_WORD, PREV1_TAG),
			
				mockToken(THIS_WORD, THIS_TAG),
			
				mockToken(NEXT1_WORD, NEXT1_TAG),
				mockToken(NEXT2_WORD, NEXT2_TAG),
				mockToken(NEXT3_WORD, NEXT3_TAG),
				mockToken(NEXT4_WORD, NEXT4_TAG),
			};
	}

	public static Token[] getAltTokens() {
		return new Token[] {
				mockToken(ALT + PREV4_WORD, ALT + PREV4_TAG),
				mockToken(ALT + PREV3_WORD, ALT + PREV3_TAG),
				mockToken(ALT + PREV2_WORD, ALT + PREV2_TAG),
				mockToken(ALT + PREV1_WORD, ALT + PREV1_TAG),
			
				mockToken(ALT + THIS_WORD, THIS_TAG),
			
				mockToken(ALT + NEXT1_WORD, ALT + NEXT1_TAG),
				mockToken(ALT + NEXT2_WORD, ALT + NEXT2_TAG),
				mockToken(ALT + NEXT3_WORD, ALT + NEXT3_TAG),
				mockToken(ALT + NEXT4_WORD, ALT + NEXT4_TAG),
			};
	}

	public static Token[] getUntaggedTokens() {
		return new Token[] {
				mockToken(PREV4_WORD, null),
				mockToken(PREV3_WORD, null),
				mockToken(PREV2_WORD, null),
				mockToken(PREV1_WORD, null),
			
				mockToken(THIS_WORD, null),
			
				mockToken(NEXT1_WORD, null),
				mockToken(NEXT2_WORD, null),
				mockToken(NEXT3_WORD, null),
				mockToken(NEXT4_WORD, null),
			};
	}

	public static Context buildContext(Token... tokens) {
		return new SentenceContext(new DefaultSentence(tokens));
	}

	public static Context buildContext(int skipTo, Token... tokens) {
		return skip(buildContext(tokens), skipTo);
	}

	public static Context skip(Context context, int skipTo) {
		for(int i = 0; i < skipTo; i++)
			context.next();
		
		return context;
	}

}
