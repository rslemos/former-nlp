package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public class NEXT1OR2WDRuleFactory extends AbstractRuleFactory {
	
	public static final NEXT1OR2WDRuleFactory INSTANCE = new NEXT1OR2WDRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		String word1 = (String) context.getToken(1).getFeature(Token.WORD);
		String word2 = (String) context.getToken(2).getFeature(Token.WORD);

		return Arrays.<Rule> asList(
				createRule(from, to, word1),
				createRule(from, to, word2)
		);
	}
	
	public Rule createRule(Object from, Object to, String next1or2Word) {
		return new NEXT1OR2WDRule(from, to, next1or2Word);
	}

	private static class NEXT1OR2WDRule extends AbstractRule {
		private final String next1or2Word;
	
		private NEXT1OR2WDRule(Object from, Object to, String next1or2Word) {
			super(from, to);
			
			this.next1or2Word = next1or2Word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word1 = (String) context.getToken(1).getFeature(Token.WORD);
			String word2 = (String) context.getToken(2).getFeature(Token.WORD);
			
			return next1or2Word != null 
			? (next1or2Word.equals(word1) | next1or2Word.equals(word2)) 
			: (word1 == null | word2 == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			NEXT1OR2WDRule other = (NEXT1OR2WDRule) o;
			
			return next1or2Word != null ? next1or2Word.equals(other.next1or2Word) : other.next1or2Word == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 13;
			hashCode += next1or2Word != null ? next1or2Word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + next1or2Word;
		}
	}
}