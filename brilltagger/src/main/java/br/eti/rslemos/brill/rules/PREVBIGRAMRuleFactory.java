package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.AbstractToken;

public class PREVBIGRAMRuleFactory extends AbstractSingleRuleFactory {
	public static final PREVBIGRAMRuleFactory INSTANCE = new PREVBIGRAMRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word_2 = (String) context.getToken(-2).getFeature(AbstractToken.WORD);
		String word_1 = (String) context.getToken(-1).getFeature(AbstractToken.WORD);

		return createRule(from, to, word_2, word_1);
	}
	
	public Rule createRule(Object from, Object to, String prev2Word, String prev1Word) {
		return new PREVBIGRAMRule(from, to, prev2Word, prev1Word);
	}

	private static class PREVBIGRAMRule extends AbstractRule {
		private final String prev2Word;
		private final String prev1Word;
	
		private PREVBIGRAMRule(Object from, Object to, String prev2Word, String prev1Word) {
			super(from, to);
			this.prev2Word = prev2Word;
			this.prev1Word = prev1Word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word_2 = (String) context.getToken(-2).getFeature(AbstractToken.WORD);
			String word_1 = (String) context.getToken(-1).getFeature(AbstractToken.WORD);
			
			return (prev2Word != null ? prev2Word.equals(word_2) : word_2 == null) &&
				(prev1Word != null ? prev1Word.equals(word_1) : word_1 == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			PREVBIGRAMRule other = (PREVBIGRAMRule) o;
			
			return (prev2Word != null ? prev2Word.equals(other.prev2Word) : other.prev2Word == null) &&
				(prev1Word != null ? prev1Word.equals(other.prev1Word) : other.prev1Word == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 11;
			hashCode += prev2Word != null ? prev2Word.hashCode() : 0;
			hashCode *= 7;
			hashCode += prev1Word != null ? prev1Word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prev2Word + " " + prev1Word;
		}
	}
}