package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public class WDNEXTTAGRuleFactory extends AbstractSingleRuleFactory {
	public static final WDNEXTTAGRuleFactory INSTANCE = new WDNEXTTAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = (String) context.getToken(0).getFeature(Token.WORD);
		Object tag1 = context.getToken(1).getFeature(Token.POS);

		return createRule(from, to, word0, tag1);
	}
	
	public Rule createRule(Object from, Object to, String word, Object next1Object) {
		return new WDNEXTTAGRule(from, to, word, next1Object);
	}

	private static class WDNEXTTAGRule extends AbstractRule {
		private final String word;
		private final Object next1Object;
	
		private WDNEXTTAGRule(Object from, Object to, String word, Object next1Object) {
			super(from, to);
			this.word = word;
			this.next1Object = next1Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word0 = (String) context.getToken(0).getFeature(Token.WORD);
			Object tag1 = context.getToken(1).getFeature(Token.POS);
			
			return (word != null ? word.equals(word0) : word0 == null) &&
				(next1Object != null ? next1Object.equals(tag1) : tag1 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(next1Object != null ? next1Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			WDNEXTTAGRule other = (WDNEXTTAGRule) o;
			
			return (word != null ? word.equals(other.word) : other.word == null) &&
				(next1Object != null ? next1Object.equals(other.next1Object) : other.next1Object == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 19;
			hashCode += word != null ? word.hashCode() : 0;
			hashCode *= 13;
			hashCode += next1Object != null ? next1Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + word + " " + next1Object;
		}
	}
}