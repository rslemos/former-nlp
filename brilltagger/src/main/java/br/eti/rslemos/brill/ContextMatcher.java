package br.eti.rslemos.brill;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.eti.rslemos.tagger.Token;

public class ContextMatcher extends BaseMatcher<Context> {
	private final int tokenPosition;
	private final Matcher<Token> tokenMatcher;
	
	public ContextMatcher(int tokenPosition, Matcher<Token> tokenMatcher) {
		super();
		this.tokenPosition = tokenPosition;
		this.tokenMatcher = tokenMatcher;
	}

	public boolean matches(Object item) {
		if (!(item instanceof Context))
			return false;
		
		Context other = (Context)item;

		Token token = other.getToken(tokenPosition);
		
		return tokenMatcher.matches(token);
	}

	public void describeTo(Description description) {
		description.appendText(String.valueOf(tokenPosition));
		description.appendText("th token from current ");
		description.appendDescriptionOf(tokenMatcher);
	}
}
