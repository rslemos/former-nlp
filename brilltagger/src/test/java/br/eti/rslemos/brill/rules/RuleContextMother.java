package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Token;

public abstract class RuleContextMother {

	public static final String TO_TAG = "to-tag";

	public static final String OTHER_WORD = "other-word";
	public static final String OTHER_TAG = "other-tag";
	public static final String NEITHER_TAG = "neither-tag";

	public static final String PREV1_WORD = "prev1-word";
	public static final String PREV1_TAG = "prev1-tag";

	public static final String THIS_WORD = "this-word";
	public static final String THIS_TAG = "this-tag";
	
	public static final String NEXT1_WORD = "next1-word";
	public static final String NEXT1_TAG = "next1-tag";

	public static final String NEXT2_TAG = "next2-tag";

	public static final String NEXT3_TAG = "next3-tag";

	public static Context buildContext() {
		Token prev3 = mock(Token.class);
		when(prev3.getWord()).thenReturn("prev3-word");
		when(prev3.getTag() ).thenReturn("prev3-tag");
	
		Token prev2 = mock(Token.class);
		when(prev2.getWord()).thenReturn("prev2-word");
		when(prev2.getTag() ).thenReturn("prev2-tag");
		
		Token prev1 = mock(Token.class);
		when(prev1.getWord()).thenReturn(RuleContextMother.PREV1_WORD);
		when(prev1.getTag() ).thenReturn(RuleContextMother.PREV1_TAG);
	
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn(RuleContextMother.THIS_WORD);
		when(token.getTag() ).thenReturn(RuleContextMother.THIS_TAG);
	
		Token next1 = mock(Token.class);
		when(next1.getWord()).thenReturn(RuleContextMother.NEXT1_WORD);
		when(next1.getTag() ).thenReturn(RuleContextMother.NEXT1_TAG);
	
		Token next2 = mock(Token.class);
		when(next2.getWord()).thenReturn("next2-word");
		when(next2.getTag() ).thenReturn(RuleContextMother.NEXT2_TAG);
		
		Token next3 = mock(Token.class);
		when(next3.getWord()).thenReturn("next3-word");
		when(next3.getTag() ).thenReturn(RuleContextMother.NEXT3_TAG);
	
		Context context = new Context(new Token[] { prev3, prev2, prev1, token, next1, next2, next3 });
		
		for(int i = 0; i < 3; i++)
			context.advance();
		
		return context;
	}

}
