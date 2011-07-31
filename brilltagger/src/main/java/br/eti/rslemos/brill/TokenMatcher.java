package br.eti.rslemos.brill;

import java.util.Set;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.eti.rslemos.tagger.Token;

public class TokenMatcher extends BaseMatcher<Token> {
	private final String featureName;
	private final Matcher<Object> featureMatcher;
	@Deprecated private final Matcher<String> wordMatcher;
	@Deprecated private final Matcher<Object> tagMatcher;
	private final Matcher<Set<String>> featureSetMatcher;
	
	private TokenMatcher(String featureName, Matcher<Object> featureMatcher, Matcher<String> wordMatcher, Matcher<Object> tagMatcher, Matcher<Set<String>> featureSetMatcher) {
		this.featureName = featureName;
		this.featureMatcher = featureMatcher;
		this.wordMatcher = wordMatcher;
		this.tagMatcher = tagMatcher;
		this.featureSetMatcher = featureSetMatcher;
	}

	public boolean matches(Object item) {
		if (!(item instanceof Token))
			return false;
		
		Token other = (Token) item;
		
		return (featureName != null ? featureMatcher.matches(other.getFeature(featureName)) : true) &&
			(wordMatcher != null ? wordMatcher.matches(other.getWord()) : true) &&
			(tagMatcher != null ? tagMatcher.matches(other.getTag()) : true) &&
			(featureSetMatcher != null ? featureSetMatcher.matches(other.getFeatures().keySet()) : true)
			;
	}

	public void describeTo(Description description) {
		String whose = "whose ";
		
		description.appendText("token ");
		if (featureName != null) {
			description.appendText(whose);
			description.appendText("feature \"").appendValue(featureName).appendText("\" ");
			description.appendDescriptionOf(featureMatcher);
			whose = "and ";
		}
		
		if (wordMatcher != null) {
			description.appendText(whose);
			description.appendText("word ");
			description.appendDescriptionOf(wordMatcher);
			whose = "and ";
		}
		
		if (tagMatcher != null) {
			description.appendText(whose);
			description.appendText("tag ");
			description.appendDescriptionOf(tagMatcher);
			whose = "and ";
		}
		
		if (featureSetMatcher != null) {
			description.appendText(whose);
			description.appendText("feature set ");
			description.appendDescriptionOf(featureSetMatcher);
			whose = "and ";
		}
		
	}

	public static TokenMatcher hasFeature(String featureName, Matcher<Object> featureMatcher) {
		return new TokenMatcher(featureName, featureMatcher, null, null, null);
	}
	
	@Deprecated
	public static TokenMatcher hasWord(Matcher<String> wordMatcher) {
		return new TokenMatcher(null, null, wordMatcher, null, null);
	}
	
	@Deprecated
	public static TokenMatcher hasTag(Matcher<Object> tagMatcher) {
		return new TokenMatcher(null, null, null, tagMatcher, null);
	}

	public static TokenMatcher hasFeatures(Matcher<Set<String>> featureSetMatcher) {
		return new TokenMatcher(null, null, null, null, featureSetMatcher);
	}
}
