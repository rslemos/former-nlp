package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class SURROUNDTAGRuleFactory extends AbstractSingleRuleFactory {
	public Rule createRule(Tag from, Tag to, Context context) {
		Tag tag_1 = context.getToken(-1).getTag();
		Tag tag1 = context.getToken(1).getTag();

		return new SURROUNDTAGRule(from, to, tag_1, tag1);
	}
}