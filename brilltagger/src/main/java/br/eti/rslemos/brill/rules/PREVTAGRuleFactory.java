package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVTAGRuleFactory extends AbstractSingleRuleFactory {
	public static final PREVTAGRuleFactory INSTANCE = new PREVTAGRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		Object tag_1 = context.getToken(-1).getTag();

		return createRule(from, to, tag_1);
	}
	
	public Rule createRule(Object from, Object to, Object prevObject) {
		return new PREVTAGRule(from, to, prevObject);
	}

	private static class PREVTAGRule extends AbstractRule {
		private final Object prevObject;
	
		private PREVTAGRule(Object from, Object to, Object prevObject) {
			super(from, to);
			this.prevObject = prevObject;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			Object tag_1 = context.getToken(-1).getTag();
			
			return prevObject != null ? prevObject.equals(tag_1) : tag_1 == null;
		}
		
		@Override
		public boolean testsTag(Object tag) {
			return super.testsTag(tag) || 
				(prevObject != null ? prevObject.equals(tag) : tag == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			PREVTAGRule other = (PREVTAGRule) o;
			
			return prevObject != null ? prevObject.equals(other.prevObject) : other.prevObject == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 53;
			hashCode += prevObject != null ? prevObject.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + prevObject;
		}
	}
}