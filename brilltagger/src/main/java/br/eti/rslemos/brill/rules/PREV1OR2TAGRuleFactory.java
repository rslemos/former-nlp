package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.AbstractToken;

public class PREV1OR2TAGRuleFactory extends AbstractRuleFactory {
	
	public static final PREV1OR2TAGRuleFactory INSTANCE = new PREV1OR2TAGRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		Object tag_1 = context.getToken(-1).getFeature(AbstractToken.POS);
		Object tag_2 = context.getToken(-2).getFeature(AbstractToken.POS);

		return Arrays.<Rule> asList(
				createRule(from, to, tag_1),
				createRule(from, to, tag_2)
		);
	}
	
	public Rule createRule(Object from, Object to, Object prev1or2Object) {
		return new PREV1OR2TAGRule(from, to, prev1or2Object);
	}

	private static class PREV1OR2TAGRule extends AbstractRule {
		private final Object prev1or2Object;
	
		private PREV1OR2TAGRule(Object from, Object to, Object prev1or2Object) {
			super(from, to);
			
			this.prev1or2Object = prev1or2Object;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			Object tag_1 = context.getToken(-1).getFeature(AbstractToken.POS);
			Object tag_2 = context.getToken(-2).getFeature(AbstractToken.POS);
			
			return prev1or2Object != null 
			? (prev1or2Object.equals(tag_1) | prev1or2Object.equals(tag_2)) 
			: (tag_1 == null | tag_2 == null);
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(prev1or2Object != null ? prev1or2Object.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			PREV1OR2TAGRule other = (PREV1OR2TAGRule) o;
			
			return prev1or2Object != null ? prev1or2Object.equals(other.prev1or2Object) : other.prev1or2Object == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 37;
			hashCode += prev1or2Object != null ? prev1or2Object.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prev1or2Object;
		}
	}
}