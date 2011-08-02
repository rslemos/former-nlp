package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public static final NEXTBIGRAMRuleFactory INSTANCE = new NEXTBIGRAMRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();

		return createRule(from, to, word1, word2);
	}
	
	public Rule createRule(Object from, Object to, String next1Word, String next2Word) {
		return new NEXTBIGRAMRule(from, to, next1Word, next2Word);
	}

	private static class NEXTBIGRAMRule extends AbstractRule {
		private final String next1Word;
		private final String next2Word;
	
		private NEXTBIGRAMRule(Object from, Object to, String next1Word, String next2Word) {
			super(from, to);
			this.next1Word = next1Word;
			this.next2Word = next2Word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word1 = context.getToken(1).getWord();
			String word2 = context.getToken(2).getWord();
			
			return (next1Word != null ? next1Word.equals(word1) : word1 == null) &&
				(next2Word != null ? next2Word.equals(word2) : word2 == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			NEXTBIGRAMRule other = (NEXTBIGRAMRule) o;
			
			return (next1Word != null ? next1Word.equals(other.next1Word) : other.next1Word == null) &&
				(next2Word != null ? next2Word.equals(other.next2Word) : other.next2Word == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 11;
			hashCode += next1Word != null ? next1Word.hashCode() : 0;
			hashCode *= 5;
			hashCode += next2Word != null ? next2Word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + next1Word + " " + next2Word;
		}
	}
}