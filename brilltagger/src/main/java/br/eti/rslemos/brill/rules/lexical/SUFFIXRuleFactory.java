package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Rule;

public class SUFFIXRuleFactory extends AbstractAFFIXRuleFactory {
	public static final SUFFIXRuleFactory INSTANCE = new SUFFIXRuleFactory();

	@Override
	protected Rule create(Object from, Object to, String word, int length) {
		return createRule(from, to, word.substring(word.length() - length, word.length()));
	}
	
	public Rule createRule(Object fromObject, Object toObject, String suffix) {
		return new SUFFIXRule(fromObject, toObject, suffix);
	}

	private static class SUFFIXRule extends AbstractLexicalBrillRule {
		private final String suffix;
	
		private SUFFIXRule(Object fromObject, Object toObject, String suffix) {
			super(fromObject, toObject);
			
			this.suffix = suffix;
		}
	
		@Override
		protected boolean thisMatches(String word0) {
			return suffix != null ? word0.endsWith(suffix) : word0 == null;
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			SUFFIXRule other = (SUFFIXRule) o;
			
			return suffix != null ? suffix.equals(other.suffix) : other.suffix == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 103;
			hashCode += suffix != null ? suffix.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + suffix;
		}
	
	}
}