package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public class RBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public static final RBIGRAMRuleFactory INSTANCE = new RBIGRAMRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = (String) context.getToken(0).getFeature(Token.WORD);
		String word1 = (String) context.getToken(1).getFeature(Token.WORD);

		return createRule(from, to, word0, word1);
	}
	
	public Rule createRule(Object from, Object to, String word, String nextWord) {
		return new RBIGRAMRule(from, to, word, nextWord);
	}

	private static class RBIGRAMRule extends AbstractRule {
		private final String word;
		private final String nextWord;
	
		private RBIGRAMRule(Object from, Object to, String word, String nextWord) {
			super(from, to);
			this.word = word;
			this.nextWord = nextWord;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word0 = (String) context.getToken(0).getFeature(Token.WORD);
			String word1 = (String) context.getToken(1).getFeature(Token.WORD);
			
			return (word != null ? word.equals(word0) : word0 == null) &&
				(nextWord != null ? nextWord.equals(word1) : word1 == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			RBIGRAMRule other = (RBIGRAMRule) o;
			
			return (word != null ? word.equals(other.word) : other.word == null) &&
				(nextWord != null ? nextWord.equals(other.nextWord) : other.nextWord == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 13;
			hashCode += word != null ? word.hashCode() : 0;
			hashCode *= 5;
			hashCode += nextWord != null ? nextWord.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + word + " " + nextWord;
		}
	}
}