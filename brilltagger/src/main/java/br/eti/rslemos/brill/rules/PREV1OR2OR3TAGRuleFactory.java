package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public class PREV1OR2OR3TAGRuleFactory extends AbstractRuleFactory {
	
	public static final PREV1OR2OR3TAGRuleFactory INSTANCE = new PREV1OR2OR3TAGRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		Object tag_1 = context.getToken(-1).getFeature(Token.POS);
		Object tag_2 = context.getToken(-2).getFeature(Token.POS);
		Object tag_3 = context.getToken(-3).getFeature(Token.POS);

		return Arrays.<Rule> asList(
				createRule(from, to, tag_1), 
				createRule(from, to, tag_2),
				createRule(from, to, tag_3)
		);
	}
	
	public Rule createRule(Object from, Object to, Object prev1or2or3Object) {
		return new PREV1OR2OR3TAGRule(from, to, prev1or2or3Object);
	}

	private static class PREV1OR2OR3TAGRule extends AbstractRule {
		private final Object prev1or2or3Object;
	
		private PREV1OR2OR3TAGRule(Object from, Object to, Object prev1or2or3Object) {
			super(from, to);
			
			this.prev1or2or3Object = prev1or2or3Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			Object tag_1 = context.getToken(-1).getFeature(Token.POS);
			Object tag_2 = context.getToken(-2).getFeature(Token.POS);
			Object tag_3 = context.getToken(-3).getFeature(Token.POS);
			
			return prev1or2or3Object != null 
			? (prev1or2or3Object.equals(tag_1) | prev1or2or3Object.equals(tag_2) | prev1or2or3Object.equals(tag_3)) 
			: (tag_1 == null | tag_2 == null | tag_3 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(prev1or2or3Object != null ? prev1or2or3Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			PREV1OR2OR3TAGRule other = (PREV1OR2OR3TAGRule) o;
			
			return prev1or2or3Object != null ? prev1or2or3Object.equals(other.prev1or2or3Object) : other.prev1or2or3Object == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 31;
			hashCode += prev1or2or3Object != null ? prev1or2or3Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prev1or2or3Object;
		}
	}
}