package br.eti.rslemos.brill;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;

public class ThresholdHaltingStrategy implements HaltingStrategy {

	private final int threshold;

	protected ThresholdHaltingStrategy(int threshold) {
		this.threshold = threshold;
	}

	private int countErrors(List<List<Token>> proofCorpus, Context[] trainingCorpus) {
		int errorCount = 0;

		int i = 0;
		for (List<Token> proofSentence : proofCorpus) {
			Context trainingSentence = trainingCorpus[i++];

			try {
				for (Token proofToken : proofSentence) {
					Token trainingToken = trainingSentence.next();

					if (!ObjectUtils.equals(proofToken.getTag(), trainingToken.getTag()))
						errorCount++;
				}
			} finally {
				trainingSentence.reset();
			}
		}

		return errorCount;
	}

	public HaltingStrategyContext getContext(final List<List<Token>> proofCorpus, final Context[] trainingCorpus) {
		return new HaltingStrategyContext() {
			private int errorCount = countErrors(proofCorpus, trainingCorpus);

			public boolean updateAndTest(Context[] trainingCorpus) {
				int errorCount = countErrors(proofCorpus, trainingCorpus);
				try {
					return !((this.errorCount - errorCount) < threshold);
				} finally {
					this.errorCount = errorCount;
				}
			}

		};
	}
}