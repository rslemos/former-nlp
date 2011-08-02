package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class LBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public static final LBIGRAMRuleFactory INSTANCE = new LBIGRAMRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = context.getToken(0).getWord();
		String word_1 = context.getToken(-1).getWord();

		return createRule(from, to, word_1, word0);
	}

	public Rule createRule(Object from, Object to, String prevWord, String word) {
		return new LBIGRAMRule(from, to, prevWord, word);
	}

	private static class LBIGRAMRule extends AbstractRule {
		private final String prevWord;
		private final String word;
	
		private LBIGRAMRule(Object from, Object to, String prevWord, String word) {
			super(from, to);
			this.prevWord = prevWord;
			this.word = word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word0 = context.getToken(0).getWord();
			String word_1 = context.getToken(-1).getWord();
			
			return (word != null ? word.equals(word0) : word0 == null) &&
				(prevWord != null ? prevWord.equals(word_1) : word_1 == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			LBIGRAMRule other = (LBIGRAMRule) o;
			
			return (word != null ? word.equals(other.word) : other.word == null) &&
			(prevWord != null ? prevWord.equals(other.prevWord) : other.prevWord == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 7;
			hashCode += word != null ? word.hashCode() : 0;
			hashCode *= 5;
			hashCode += prevWord != null ? prevWord.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prevWord + " " + word;
		}
	}
}