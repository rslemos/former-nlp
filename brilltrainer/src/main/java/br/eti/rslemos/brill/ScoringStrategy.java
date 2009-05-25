package br.eti.rslemos.brill;

import java.util.List;

import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;

public interface ScoringStrategy {
	int compute(List<List<Token>> proofCorpus, BufferingContext[] workCorpus, Rule rule);
}