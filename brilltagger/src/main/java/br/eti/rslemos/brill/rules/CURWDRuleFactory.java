package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class CURWDRuleFactory extends AbstractSingleRuleFactory {
	public static final CURWDRuleFactory INSTANCE = new CURWDRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = context.getToken(0).getWord();

		return createRule(from, to, word0);
	}
	
	public Rule createRule(Object from, Object to, String word) {
		return new CURWDRule(from, to, word);
	}

	private static class CURWDRule extends AbstractRule {
		private final String word;
	
		private CURWDRule(Object from, Object to, String word) {
			super(from, to);
			this.word = word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word0 = context.getToken(0).getWord();
			
			return word != null ? word.equals(word0) : word0 == null;
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			CURWDRule other = (CURWDRule) o;
			
			return word != null ? word.equals(other.word) : other.word == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 5;
			hashCode += word != null ? word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + word;
		}
	}
}
