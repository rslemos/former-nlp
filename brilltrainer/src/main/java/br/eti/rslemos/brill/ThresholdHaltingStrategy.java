package br.eti.rslemos.brill;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.RulesetTrainer.HaltingStrategy;
import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

public class ThresholdHaltingStrategy implements HaltingStrategy {

	private final int threshold;
	
	private TrainingContext trainingContext;
	private int errorCount = -1;

	protected ThresholdHaltingStrategy(int threshold) {
		this.threshold = threshold;
	}

	public void setTrainingContext(TrainingContext trainingContext) {
		this.trainingContext = trainingContext;
		if (trainingContext != null)
			this.errorCount = countErrors();
		else
			this.errorCount = -1;
	}

	private int countErrors() {
		int errorCount = 0;

		int i = 0;
		for (List<Token> proofSentence : trainingContext.proofCorpus) {
			Context trainingSentence = trainingContext.trainingCorpus[i++];

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

	public boolean updateAndTest() {
		int errorCount = countErrors();
		try {
			return !((this.errorCount - errorCount) < threshold);
		} finally {
			this.errorCount = errorCount;
		}
	}
}