package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class NEXTWDRuleFactory extends AbstractSingleRuleFactory {
	public Rule createRule(Tag from, Tag to, Context context) {
		String word1 = context.getToken(1).getWord();

		return new NEXTWDRule(from, to, word1);
	}
}