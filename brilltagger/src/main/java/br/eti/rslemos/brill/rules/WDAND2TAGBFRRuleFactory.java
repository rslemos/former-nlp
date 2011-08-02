package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public class WDAND2TAGBFRRuleFactory extends AbstractSingleRuleFactory {
	public static final WDAND2TAGBFRRuleFactory INSTANCE = new WDAND2TAGBFRRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		Object tag_2 = context.getToken(-2).getFeature(Token.POS);
		String word0 = (String) context.getToken(0).getFeature(Token.WORD);

		return createRule(from, to, tag_2, word0);
	}
	
	public Rule createRule(Object from, Object to, Object prev2Object, String word) {
		return new WDAND2TAGBFRRule(from, to, prev2Object, word);
	}

	private static class WDAND2TAGBFRRule extends AbstractRule {
		private final String word;
		private final Object prev2Object;
	
		private WDAND2TAGBFRRule(Object from, Object to, Object prev2Object, String word) {
			super(from, to);
			this.word = word;
			this.prev2Object = prev2Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			Object tag_2 = context.getToken(-2).getFeature(Token.POS);
			String word0 = (String) context.getToken(0).getFeature(Token.WORD);
			
			return (word != null ? word.equals(word0) : word0 == null) &&
				(prev2Object != null ? prev2Object.equals(tag_2) : tag_2 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(prev2Object != null ? prev2Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			WDAND2TAGBFRRule other = (WDAND2TAGBFRRule) o;
			
			return (word != null ? word.equals(other.word) : other.word == null) &&
				(prev2Object != null ? prev2Object.equals(other.prev2Object) : other.prev2Object == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 19;
			hashCode += word != null ? word.hashCode() : 0;
			hashCode *= 11;
			hashCode += prev2Object != null ? prev2Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prev2Object + " " + word;
		}
	}
}