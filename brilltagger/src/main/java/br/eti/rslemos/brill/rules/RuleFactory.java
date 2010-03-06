package br.eti.rslemos.brill.rules;

import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public interface RuleFactory<T> {

	Collection<Rule<T>> create(Context<T> context, Token<T> target) throws RuleCreationException;

}