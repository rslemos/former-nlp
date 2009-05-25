package br.eti.rslemos.brill;

import java.util.List;

public interface HaltingStrategy {
	static interface HaltingStrategyContext {
		boolean updateAndTest(Context[] workCorpus);
	}
	
	HaltingStrategy.HaltingStrategyContext getContext(List<List<Token>> proofCorpus, Context[] workCorpus);
}