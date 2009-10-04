package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import br.eti.rslemos.brill.ArrayContext;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Token;

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
		Token prev4 = mock(Token.class);
		when(prev4.getWord()).thenReturn(PREV4_WORD);
		when(prev4.getTag() ).thenReturn(PREV4_TAG);
	
		Token prev3 = mock(Token.class);
		when(prev3.getWord()).thenReturn(PREV3_WORD);
		when(prev3.getTag() ).thenReturn(PREV3_TAG);
	
		Token prev2 = mock(Token.class);
		when(prev2.getWord()).thenReturn(PREV2_WORD);
		when(prev2.getTag() ).thenReturn(PREV2_TAG);
		
		Token prev1 = mock(Token.class);
		when(prev1.getWord()).thenReturn(PREV1_WORD);
		when(prev1.getTag() ).thenReturn(PREV1_TAG);
	
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn(THIS_WORD);
		when(token.getTag() ).thenReturn(THIS_TAG);
	
		Token next1 = mock(Token.class);
		when(next1.getWord()).thenReturn(NEXT1_WORD);
		when(next1.getTag() ).thenReturn(NEXT1_TAG);
	
		Token next2 = mock(Token.class);
		when(next2.getWord()).thenReturn(NEXT2_WORD);
		when(next2.getTag() ).thenReturn(NEXT2_TAG);
		
		Token next3 = mock(Token.class);
		when(next3.getWord()).thenReturn(NEXT3_WORD);
		when(next3.getTag() ).thenReturn(NEXT3_TAG);
	
		Token next4 = mock(Token.class);
		when(next4.getWord()).thenReturn(NEXT4_WORD);
		when(next4.getTag() ).thenReturn(NEXT4_TAG);
	
		return buildContext0(prev4, prev3, prev2, prev1, token, next1, next2, next3, next4);
	}

	public static Context buildAltContext() {
		Token prev4 = mock(Token.class);
		when(prev4.getWord()).thenReturn(ALT + PREV4_WORD);
		when(prev4.getTag() ).thenReturn(ALT + PREV4_TAG);
	
		Token prev3 = mock(Token.class);
		when(prev3.getWord()).thenReturn(ALT + PREV3_WORD);
		when(prev3.getTag() ).thenReturn(ALT + PREV3_TAG);
	
		Token prev2 = mock(Token.class);
		when(prev2.getWord()).thenReturn(ALT + PREV2_WORD);
		when(prev2.getTag() ).thenReturn(ALT + PREV2_TAG);
		
		Token prev1 = mock(Token.class);
		when(prev1.getWord()).thenReturn(ALT + PREV1_WORD);
		when(prev1.getTag() ).thenReturn(ALT + PREV1_TAG);
	
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn(ALT + THIS_WORD);
		when(token.getTag() ).thenReturn(THIS_TAG);
	
		Token next1 = mock(Token.class);
		when(next1.getWord()).thenReturn(ALT + NEXT1_WORD);
		when(next1.getTag() ).thenReturn(ALT + NEXT1_TAG);
	
		Token next2 = mock(Token.class);
		when(next2.getWord()).thenReturn(ALT + NEXT2_WORD);
		when(next2.getTag() ).thenReturn(ALT + NEXT2_TAG);
		
		Token next3 = mock(Token.class);
		when(next3.getWord()).thenReturn(ALT + NEXT3_WORD);
		when(next3.getTag() ).thenReturn(ALT + NEXT3_TAG);
	
		Token next4 = mock(Token.class);
		when(next4.getWord()).thenReturn(ALT + NEXT4_WORD);
		when(next4.getTag() ).thenReturn(ALT + NEXT4_TAG);
	
		return buildContext0(prev4, prev3, prev2, prev1, token, next1, next2, next3, next4);
	}

	public static Context buildUntaggedContext() {
		Token prev4 = mock(Token.class);
		when(prev4.getWord()).thenReturn(PREV4_WORD);
	
		Token prev3 = mock(Token.class);
		when(prev3.getWord()).thenReturn(PREV3_WORD);
	
		Token prev2 = mock(Token.class);
		when(prev2.getWord()).thenReturn(PREV2_WORD);
		
		Token prev1 = mock(Token.class);
		when(prev1.getWord()).thenReturn(PREV1_WORD);
	
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn(THIS_WORD);
	
		Token next1 = mock(Token.class);
		when(next1.getWord()).thenReturn(NEXT1_WORD);
	
		Token next2 = mock(Token.class);
		when(next2.getWord()).thenReturn(NEXT2_WORD);
		
		Token next3 = mock(Token.class);
		when(next3.getWord()).thenReturn(NEXT3_WORD);
	
		Token next4 = mock(Token.class);
		when(next4.getWord()).thenReturn(NEXT4_WORD);
	
		return buildContext0(prev4, prev3, prev2, prev1, token, next1, next2, next3, next4);
	}

	private static Context buildContext0(Token prev4, Token prev3, Token prev2, Token prev1,
			Token token, Token next1, Token next2, Token next3, Token next4) {
		Context context = new ArrayContext(new Token[] { prev4, prev3, prev2, prev1, token, next1, next2, next3, next4 });
		
		for(int i = 0; i < 5; i++)
			context.next();
		
		return context;
	}

}
