package br.eti.rslemos.brill.events;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.*;

import java.util.Collection;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.BrillTrainer.Pair;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public class BrillCustomMatchers {
	private BrillCustomMatchers() {}
	
	/* Token */
	public static Matcher<Token> sameWord(final Token token) {
		return new BaseMatcher<Token>() {
			public boolean matches(Object item) {
				if (!(item instanceof Token))
					return false;
				
				Token other = (Token) item;
				
				String word = token.getWord();
				String otherWord = other.getWord();
				
				return
					(word != null ? word.equals(otherWord) : otherWord == null);
			}

			public void describeTo(Description description) {
				description.appendText("same word as ").appendValue(token);
			}
		};
	}
	
	public static Matcher<Token> sameTag(final Token token) {
		return new BaseMatcher<Token>() {
			public boolean matches(Object item) {
				if (!(item instanceof Token))
					return false;
				
				Token other = (Token) item;
				
				Object tag = token.getTag();
				Object otherTag = other.getTag();
				
				return
					(tag != null ? tag.equals(otherTag) : otherTag == null);
			}

			public void describeTo(Description description) {
				description.appendText("same tag as ").appendValue(token);
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public static Matcher<Token> tokenExternallyEquals(Token token) {
		return allOf(sameWord(token), sameTag(token));
	}

	/* Sentence */
	public static Sentence anySentence() {
		return anyObject();
	}

	private static boolean sameWords(Sentence x, Sentence y) {
		if (x.size() != y.size())
			return false;
		
		for (Pair<Token, Token> pair : BrillTrainer.pairOf(x, y)) {
			if (!pair.x.getWord().equals(pair.y.getWord()))
				return false;
		}
		
		return true;
	}

	private static boolean taggedAs(Object baseTag, Sentence sentence) {
		for (Token token : sentence) {
			if (token.getTag() != baseTag)
				return false;
		}
		
		return true;
	}


	public static Matcher<Sentence> sameWords(final Sentence sentence) {
		return new BaseMatcher<Sentence>() {
			public boolean matches(Object item) {
				if (!(item instanceof Sentence))
					return false;
				
				Sentence other = (Sentence) item;

				return sameWords(sentence, other);
			}

			public void describeTo(Description description) {
				description.appendText("same words as ").appendValue(sentence);
			}
		};
	}

	public static Matcher<Sentence> taggedAs(final Object baseTag) {
		return new BaseMatcher<Sentence>() {
			public boolean matches(Object item) {
				if (!(item instanceof Sentence))
					return false;
				
				Sentence other = (Sentence) item;

				return taggedAs(baseTag, other);
			}

			public void describeTo(Description description) {
				description.appendText("tagged as ").appendValue(baseTag);
			}
		};
	}

	/* List<Sentence> */
	public static Matcher<List<Sentence>> sameWords(final List<Sentence> proofCorpus) {
		return new BaseMatcher<List<Sentence>>() {
			@SuppressWarnings("unchecked")
			public boolean matches(Object item) {
				if (!(item instanceof List))
					return false;
				
				List<Sentence> other = (List<Sentence>) item;

				for (Pair<Sentence, Sentence> pair : BrillTrainer.pairOf(proofCorpus, other)) {
					if (!sameWords(pair.x, pair.y))
						return false;
				}
				
				return true;
			}

			public void describeTo(Description description) {
				description.appendText("same words as ").appendValue(proofCorpus);
			}
		};
	}

	public static Matcher<List<Sentence>> sentencesTaggedAs(final Object baseTag) {
		return new BaseMatcher<List<Sentence>>() {
			@SuppressWarnings("unchecked")
			public boolean matches(Object item) {
				if (!(item instanceof List))
					return false;
				
				List<Sentence> other = (List<Sentence>) item;

				for (Sentence sentence : other) {
					if (!taggedAs(baseTag, sentence))
						return false;
				}
				
				return true;
			}

			public void describeTo(Description description) {
				description.appendText("tagged as ").appendValue(baseTag);
			}
		};
	}

	/* Context */
	public static Context anyContext() {
		return anyObject();
	}

	/* Collection */
	public static Matcher<Collection<?>> whichSize(final Matcher<Integer> wantedSize) {
		return new BaseMatcher<Collection<?>>() {
			@SuppressWarnings("unchecked")
			public boolean matches(Object item) {
				if (!(item instanceof Collection))
					return false;
				
				Collection<?> other = (Collection<?>) item;
				
				return wantedSize.matches(other.size());
			}

			public void describeTo(Description description) {
				description.appendText("collection which size ").appendDescriptionOf(wantedSize);
			}
		};
	}
}
