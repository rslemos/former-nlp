package br.eti.rslemos.brill.rules;

import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public interface RuleFactory {

	Collection<Rule> create(Context context, Token target);

}