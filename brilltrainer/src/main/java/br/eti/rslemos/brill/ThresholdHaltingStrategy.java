package br.eti.rslemos.brill;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;

public class ThresholdHaltingStrategy implements HaltingStrategy {
	
	private final int threshold;
	
	protected ThresholdHaltingStrategy(int threshold) {
		this.threshold = threshold;
	}

	private int countErrors(List<List<Token>> proofCorpus, Context[] workCorpus) {
		int errorCount = 0;
		
		int i = 0;
		for (List<Token> proofSentence : proofCorpus) {
			Context workSentence = workCorpus[i++];

			int j = 0;
			for (Token proofToken : proofSentence) {
				Token workToken = workSentence.getToken(j++);
				
				if (!ObjectUtils.equals(proofToken.getTag(), workToken.getTag()))
					errorCount++;
			}
		}
		
		return errorCount;
	}

	public HaltingStrategyContext getContext(final List<List<Token>> proofCorpus, final Context[] workCorpus) {
		return new HaltingStrategyContext() {
			private int errorCount = countErrors(proofCorpus, workCorpus);
			
			public boolean updateAndTest(Context[] workCorpus) {
				int errorCount = countErrors(proofCorpus, workCorpus);
				try {
					return !((this.errorCount - errorCount) < threshold);
				} finally {
					this.errorCount = errorCount;
				}
			}
			
		};
	}
}