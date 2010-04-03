package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.SentenceContext;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultTag;
import br.eti.rslemos.tagger.Token;


public abstract class RuleContextMother {

	public static final String ALT = "alt-";
	
	public static final DefaultTag TO_TAG = new DefaultTag("to-tag");

	public static final String PREV4_WORD = "prev4-word4";
	public static final DefaultTag PREV4_TAG = new DefaultTag("prev4-tag4");
	public static final DefaultTag ALT_PREV4_TAG = new DefaultTag("alt-prev4-tag4");

	public static final String PREV3_WORD = "prev3-word3";
	public static final DefaultTag PREV3_TAG = new DefaultTag("prev3-tag3");
	public static final DefaultTag ALT_PREV3_TAG = new DefaultTag("alt-prev3-tag3");

	public static final String PREV2_WORD = "prev2-word2";
	public static final DefaultTag PREV2_TAG = new DefaultTag("prev2-tag2");
	public static final DefaultTag ALT_PREV2_TAG = new DefaultTag("alt-prev2-tag2");

	public static final String PREV1_WORD = "prev1-word1";
	public static final DefaultTag PREV1_TAG = new DefaultTag("prev1-tag1");
	public static final DefaultTag ALT_PREV1_TAG = new DefaultTag("alt-prev1-tag1");

	public static final String THIS_WORD = "this-word";
	public static final DefaultTag THIS_TAG = new DefaultTag("this-tag");
	
	public static final String NEXT1_WORD = "next1-word1";
	public static final DefaultTag NEXT1_TAG = new DefaultTag("next1-tag1");
	public static final DefaultTag ALT_NEXT1_TAG = new DefaultTag("alt-next1-tag1");

	public static final String NEXT2_WORD = "next2-word2";
	public static final DefaultTag NEXT2_TAG = new DefaultTag("next2-tag2");
	public static final DefaultTag ALT_NEXT2_TAG = new DefaultTag("alt-next2-tag2");

	public static final String NEXT3_WORD = "next3-word3";
	public static final DefaultTag NEXT3_TAG = new DefaultTag("next3-tag3");
	public static final DefaultTag ALT_NEXT3_TAG = new DefaultTag("alt-next3-tag3");

	public static final String NEXT4_WORD = "next4-word4";
	public static final DefaultTag NEXT4_TAG = new DefaultTag("next4-tag4");
	public static final DefaultTag ALT_NEXT4_TAG = new DefaultTag("alt-next4-tag4");

	public static Context buildContext() {
		return buildContext(5, RuleContextMother.getStandardTokens());
	}

	public static Context buildAltContext() {
		return buildContext(5, getAltTokens());
	}

	public static Context buildUntaggedContext() {
		return buildContext(5, RuleContextMother.getUntaggedTokens());
	}

	private static Token mockToken(String word, DefaultTag tag) {
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
				mockToken(ALT + PREV4_WORD, ALT_PREV4_TAG),
				mockToken(ALT + PREV3_WORD, ALT_PREV3_TAG),
				mockToken(ALT + PREV2_WORD, ALT_PREV2_TAG),
				mockToken(ALT + PREV1_WORD, ALT_PREV1_TAG),
			
				mockToken(ALT + THIS_WORD, THIS_TAG),
			
				mockToken(ALT + NEXT1_WORD, ALT_NEXT1_TAG),
				mockToken(ALT + NEXT2_WORD, ALT_NEXT2_TAG),
				mockToken(ALT + NEXT3_WORD, ALT_NEXT3_TAG),
				mockToken(ALT + NEXT4_WORD, ALT_NEXT4_TAG),
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
		return new SentenceContext(new DefaultSentence(Arrays.asList(tokens)));
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
