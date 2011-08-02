package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public class PREVWDRuleFactory extends AbstractSingleRuleFactory {
	public static final PREVWDRuleFactory INSTANCE = new PREVWDRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word_1 = (String) context.getToken(-1).getFeature(Token.WORD);

		return createRule(from, to, word_1);
	}
	
	public Rule createRule(Object from, Object to, String prevWord) {
		return new PREVWDRule(from, to, prevWord);
	}

	private static class PREVWDRule extends AbstractRule {
		private final String prevWord;
	
		private PREVWDRule(Object from, Object to, String prevWord) {
			super(from, to);
			this.prevWord = prevWord;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word_1 = (String) context.getToken(-1).getFeature(Token.WORD);
			
			return prevWord != null ? prevWord.equals(word_1) : word_1 == null;
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			PREVWDRule other = (PREVWDRule) o;
			
			return prevWord != null ? prevWord.equals(other.prevWord) : other.prevWord == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 59;
			hashCode += prevWord != null ? prevWord.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prevWord;
		}
	}
}