package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class WDAND2TAGAFTRuleFactory extends AbstractSingleRuleFactory {
	public Rule createRule(Tag from, Tag to, Context context) {
		String word0 = context.getToken(0).getWord();
		Tag tag2 = context.getToken(2).getTag();

		return new WDAND2TAGAFTRule(from, to, word0, tag2);
	}
}