package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import br.eti.rslemos.brill.ArrayContext;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Token;

public abstract class RuleContextMother {

	public static final String TO_TAG = "to-tag";

	public static final String PREV3_WORD = "prev3-word";
	public static final String PREV3_TAG = "prev3-tag";

	public static final String PREV2_WORD = "prev2-word";
	public static final String PREV2_TAG = "prev2-tag";

	public static final String PREV1_WORD = "prev1-word";
	public static final String PREV1_TAG = "prev1-tag";

	public static final String THIS_WORD = "this-word";
	public static final String THIS_TAG = "this-tag";
	
	public static final String NEXT1_WORD = "next1-word";
	public static final String NEXT1_TAG = "next1-tag";

	public static final String NEXT2_WORD = "next2-word";
	public static final String NEXT2_TAG = "next2-tag";

	public static final String NEXT3_WORD = "next3-word";
	public static final String NEXT3_TAG = "next3-tag";

	public static Context buildContext() {
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
	
		Context context = new ArrayContext(new Token[] { prev3, prev2, prev1, token, next1, next2, next3 });
		
		for(int i = 0; i < 4; i++)
			context.next();
		
		return context;
	}

}
