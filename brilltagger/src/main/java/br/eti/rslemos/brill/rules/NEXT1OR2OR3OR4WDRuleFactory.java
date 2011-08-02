package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public class NEXT1OR2OR3OR4WDRuleFactory extends AbstractRuleFactory {
	
	public static final NEXT1OR2OR3OR4WDRuleFactory INSTANCE = new NEXT1OR2OR3OR4WDRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		String word1 = (String) context.getToken(1).getFeature(Token.WORD);
		String word2 = (String) context.getToken(2).getFeature(Token.WORD);
		String word3 = (String) context.getToken(3).getFeature(Token.WORD);
		String word4 = (String) context.getToken(4).getFeature(Token.WORD);

		return Arrays.<Rule> asList(
				createRule(from, to, word1), 
				createRule(from, to, word2),
				createRule(from, to, word3),
				createRule(from, to, word4)
		);
	}
	
	public Rule createRule(Object from, Object to, String next1or2or3or4Word) {
		return new NEXT1OR2OR3OR4WDRule(from, to, next1or2or3or4Word);
	}

	private static class NEXT1OR2OR3OR4WDRule extends AbstractRule {
		private final String next1or2or3or4Word;
	
		private NEXT1OR2OR3OR4WDRule(Object from, Object to, String next1or2or3or4Word) {
			super(from, to);
			
			this.next1or2or3or4Word = next1or2or3or4Word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word1 = (String) context.getToken(1).getFeature(Token.WORD);
			String word2 = (String) context.getToken(2).getFeature(Token.WORD);
			String word3 = (String) context.getToken(3).getFeature(Token.WORD);
			String word4 = (String) context.getToken(4).getFeature(Token.WORD);
			
			return next1or2or3or4Word != null 
			? (next1or2or3or4Word.equals(word1) | next1or2or3or4Word.equals(word2) | next1or2or3or4Word.equals(word3) | next1or2or3or4Word.equals(word4)) 
			: (word1 == null | word2 == null | word3 == null | word4 == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			NEXT1OR2OR3OR4WDRule other = (NEXT1OR2OR3OR4WDRule) o;
			
			return next1or2or3or4Word != null ? next1or2or3or4Word.equals(other.next1or2or3or4Word) : other.next1or2or3or4Word == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 67;
			hashCode += next1or2or3or4Word != null ? next1or2or3or4Word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + next1or2or3or4Word;
		}
	}
}