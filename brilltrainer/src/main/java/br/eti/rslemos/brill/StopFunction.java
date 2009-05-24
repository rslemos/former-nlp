package br.eti.rslemos.brill;

import java.util.List;

public interface StopFunction {
	static interface StopContext {
		boolean updateAndTest(Context[] workCorpus);
	}
	
	StopFunction.StopContext getContext(List<List<Token>> proofCorpus, Context[] workCorpus);
}