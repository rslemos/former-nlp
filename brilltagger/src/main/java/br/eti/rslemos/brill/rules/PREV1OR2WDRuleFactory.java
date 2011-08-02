package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.AbstractToken;

public class PREV1OR2WDRuleFactory extends AbstractRuleFactory {
	
	public static final PREV1OR2WDRuleFactory INSTANCE = new PREV1OR2WDRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		String word_1 = (String) context.getToken(-1).getFeature(AbstractToken.WORD);
		String word_2 = (String) context.getToken(-2).getFeature(AbstractToken.WORD);

		return Arrays.<Rule> asList(
				createRule(from, to, word_1),
				createRule(from, to, word_2)
		);
	}
	
	public Rule createRule(Object from, Object to, String prev1or2Word) {
		return new PREV1OR2WDRule(from, to, prev1or2Word);
	}

	private static class PREV1OR2WDRule extends AbstractRule {
		private final String prev1or2Word;
	
		private PREV1OR2WDRule(Object from, Object to, String prev1or2Word) {
			super(from, to);
			
			this.prev1or2Word = prev1or2Word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word_1 = (String) context.getToken(-1).getFeature(AbstractToken.WORD);
			String word_2 = (String) context.getToken(-2).getFeature(AbstractToken.WORD);
			
			return prev1or2Word != null 
			? (prev1or2Word.equals(word_1) | prev1or2Word.equals(word_2)) 
			: (word_1 == null | word_2 == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			PREV1OR2WDRule other = (PREV1OR2WDRule) o;
			
			return prev1or2Word != null ? prev1or2Word.equals(other.prev1or2Word) : other.prev1or2Word == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 41;
			hashCode += prev1or2Word != null ? prev1or2Word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prev1or2Word;
		}
	}
}