package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTWDRuleFactory extends AbstractSingleRuleFactory {
	public static final NEXTWDRuleFactory INSTANCE = new NEXTWDRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word1 = context.getToken(1).getWord();

		return createRule(from, to, word1);
	}
	
	public Rule createRule(Object from, Object to, String nextWord) {
		return new NEXTWDRule(from, to, nextWord);
	}

	private static class NEXTWDRule extends AbstractRule {
		private final String nextWord;
	
		private NEXTWDRule(Object from, Object to, String nextWord) {
			super(from, to);
			
			this.nextWord = nextWord;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word1 = context.getToken(1).getWord();
			
			return nextWord != null ? nextWord.equals(word1) : word1 == null;
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			NEXTWDRule other = (NEXTWDRule) o;
			
			return nextWord != null ? nextWord.equals(other.nextWord) : other.nextWord == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 29;
			hashCode += nextWord != null ? nextWord.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + nextWord;
		}
	}
}