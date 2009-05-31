package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGBFRRule extends AbstractRule implements SerializableAsBrillText  {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String tag_2 = context.getToken(-2).getTag();
			String word0 = context.getToken(0).getWord();
			
			return new WDAND2TAGBFRRule(from, to, tag_2, word0);
		}
		
	};

	private final String word;
	private final String prev2Tag;

	public WDAND2TAGBFRRule(String from, String to, String prev2Tag, String word) {
		super(from, to);
		this.word = word;
		this.prev2Tag = prev2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag_2 = context.getToken(-2).getTag();
		String word0 = context.getToken(0).getWord();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(prev2Tag != null ? prev2Tag.equals(tag_2) : tag_2 == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		WDAND2TAGBFRRule other = (WDAND2TAGBFRRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(prev2Tag != null ? prev2Tag.equals(other.prev2Tag) : other.prev2Tag == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 19;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 11;
		hashCode += prev2Tag != null ? prev2Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev2Tag + " " + word;
	}
}
