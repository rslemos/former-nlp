package br.eti.rslemos.brill.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Token;

public abstract class RuleContextMother {

	public static Context buildContext() {
		Token prev3 = mock(Token.class);
		when(prev3.getWord()).thenReturn("prev3-word");
		when(prev3.getTag() ).thenReturn("prev3-tag");
	
		Token prev2 = mock(Token.class);
		when(prev2.getWord()).thenReturn("prev2-word");
		when(prev2.getTag() ).thenReturn("prev2-tag");
		
		Token prev1 = mock(Token.class);
		when(prev1.getWord()).thenReturn("prev1-word");
		when(prev1.getTag() ).thenReturn("prev1-tag");
	
		Token token = mock(Token.class);
		when(token.getWord()).thenReturn("this-word");
		when(token.getTag() ).thenReturn("this-tag");
	
		Token next1 = mock(Token.class);
		when(next1.getWord()).thenReturn("next1-word");
		when(next1.getTag() ).thenReturn("next1-tag");
	
		Token next2 = mock(Token.class);
		when(next2.getWord()).thenReturn("next2-word");
		when(next2.getTag() ).thenReturn("next2-tag");
		
		Token next3 = mock(Token.class);
		when(next3.getWord()).thenReturn("next3-word");
		when(next3.getTag() ).thenReturn("next3-tag");
	
		Context context = new Context(new Token[] { prev3, prev2, prev1, token, next1, next2, next3 });
		
		for(int i = 0; i < 3; i++)
			context.advance();
		
		return context;
	}

}
