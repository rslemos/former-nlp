package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class PREV2TAGRuleFactory extends AbstractSingleRuleFactory {
	public Rule createRule(Tag from, Tag to, Context context) {
		Tag tag_2 = context.getToken(-2).getTag();

		return new PREV2TAGRule(from, to, tag_2);
	}
}