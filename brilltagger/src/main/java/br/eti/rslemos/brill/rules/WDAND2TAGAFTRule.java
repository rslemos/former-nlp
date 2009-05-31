package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2TAGAFTRule extends AbstractRule implements Rule {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word0 = context.getToken(0).getWord();
			String tag2 = context.getToken(2).getTag();
			
			return new WDAND2TAGAFTRule(from, to, word0, tag2);
		}
		
	};

	private final String word;
	private final String next2Tag;

	public WDAND2TAGAFTRule(String from, String to, String word, String next2Tag) {
		super(from, to);
		this.word = word;
		this.next2Tag = next2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String tag2 = context.getToken(2).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next2Tag != null ? next2Tag.equals(tag2) : tag2 == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		WDAND2TAGAFTRule other = (WDAND2TAGAFTRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(next2Tag != null ? next2Tag.equals(other.next2Tag) : other.next2Tag == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 19;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 7;
		hashCode += next2Tag != null ? next2Tag.hashCode() : 0;
		
		return hashCode;
	}
}
