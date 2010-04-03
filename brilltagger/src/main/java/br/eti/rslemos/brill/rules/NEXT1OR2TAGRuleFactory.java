package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class NEXT1OR2TAGRuleFactory extends AbstractRuleFactory {
	
	public Collection<Rule> create(Tag from, Tag to, Context context) {
		Tag tag1 = context.getToken(1).getTag();
		Tag tag2 = context.getToken(2).getTag();

		return Arrays.<Rule> asList(
				new NEXT1OR2TAGRule(from, to, tag1),
				new NEXT1OR2TAGRule(from, to, tag2)
		);
	}
}