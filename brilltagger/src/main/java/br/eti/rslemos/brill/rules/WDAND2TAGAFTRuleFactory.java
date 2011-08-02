package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.AbstractToken;

public class WDAND2TAGAFTRuleFactory extends AbstractSingleRuleFactory {
	public static final WDAND2TAGAFTRuleFactory INSTANCE = new WDAND2TAGAFTRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = (String) context.getToken(0).getFeature(AbstractToken.WORD);
		Object tag2 = context.getToken(2).getFeature(AbstractToken.POS);

		return createRule(from, to, word0, tag2);
	}
	
	public Rule createRule(Object from, Object to, String word, Object next2Object) {
		return new WDAND2TAGAFTRule(from, to, word, next2Object);
	}

	private static class WDAND2TAGAFTRule extends AbstractRule {
		private final String word;
		private final Object next2Object;
	
		private WDAND2TAGAFTRule(Object from, Object to, String word, Object next2Object) {
			super(from, to);
			this.word = word;
			this.next2Object = next2Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word0 = (String) context.getToken(0).getFeature(AbstractToken.WORD);
			Object tag2 = context.getToken(2).getFeature(AbstractToken.POS);
			
			return (word != null ? word.equals(word0) : word0 == null) &&
				(next2Object != null ? next2Object.equals(tag2) : tag2 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(next2Object != null ? next2Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			WDAND2TAGAFTRule other = (WDAND2TAGAFTRule) o;
			
			return (word != null ? word.equals(other.word) : other.word == null) &&
				(next2Object != null ? next2Object.equals(other.next2Object) : other.next2Object == null);
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 19;
			hashCode += word != null ? word.hashCode() : 0;
			hashCode *= 7;
			hashCode += next2Object != null ? next2Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + word + " " + next2Object;
		}
	}
}