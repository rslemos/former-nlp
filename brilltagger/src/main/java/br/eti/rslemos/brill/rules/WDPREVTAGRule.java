package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDPREVTAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final AbstractRuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word0 = context.getToken(0).getWord();
			String tag_1 = context.getToken(-1).getTag();
			
			return new WDPREVTAGRule(from, to, tag_1, word0);
		}
		
	};
	
	private final String word;
	private final String prevTag;

	public WDPREVTAGRule(String from, String to, String prevTag, String word) {
		super(from, to);
		this.word = word;
		this.prevTag = prevTag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String tag_1 = context.getToken(-1).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prevTag != null ? prevTag.equals(tag_1) : tag_1 == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		WDPREVTAGRule other = (WDPREVTAGRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(prevTag != null ? prevTag.equals(other.prevTag) : other.prevTag == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 23;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 5;
		hashCode += prevTag != null ? prevTag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prevTag + " " + word;
	}
}
