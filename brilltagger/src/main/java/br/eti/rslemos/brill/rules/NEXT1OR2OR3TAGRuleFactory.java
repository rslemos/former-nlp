package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.AbstractToken;

public class NEXT1OR2OR3TAGRuleFactory extends AbstractRuleFactory {
	
	public static final NEXT1OR2OR3TAGRuleFactory INSTANCE = new NEXT1OR2OR3TAGRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		Object tag1 = context.getToken(1).getFeature(AbstractToken.POS);
		Object tag2 = context.getToken(2).getFeature(AbstractToken.POS);
		Object tag3 = context.getToken(3).getFeature(AbstractToken.POS);

		return Arrays.<Rule> asList(
				createRule(from, to, tag1), 
				createRule(from, to, tag2),
				createRule(from, to, tag3)
		);
	}
	
	public Rule createRule(Object from, Object to, Object next1or2or3Object) {
		return new NEXT1OR2OR3TAGRule(from, to, next1or2or3Object);
	}

	private static class NEXT1OR2OR3TAGRule extends AbstractRule {
		private final Object next1or2or3Object;
	
		private NEXT1OR2OR3TAGRule(Object from, Object to, Object next1or2or3Object) {
			super(from, to);
			
			this.next1or2or3Object = next1or2or3Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			Object tag1 = context.getToken(1).getFeature(AbstractToken.POS);
			Object tag2 = context.getToken(2).getFeature(AbstractToken.POS);
			Object tag3 = context.getToken(3).getFeature(AbstractToken.POS);
			
			return next1or2or3Object != null 
			? (next1or2or3Object.equals(tag1) | next1or2or3Object.equals(tag2) | next1or2or3Object.equals(tag3)) 
			: (tag1 == null | tag2 == null | tag3 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(next1or2or3Object != null ? next1or2or3Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			NEXT1OR2OR3TAGRule other = (NEXT1OR2OR3TAGRule) o;
			
			return next1or2or3Object != null ? next1or2or3Object.equals(other.next1or2or3Object) : other.next1or2or3Object == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 7;
			hashCode += next1or2or3Object != null ? next1or2or3Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + next1or2or3Object;
		}
	}
}