package br.eti.rslemos.brill.stats;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.ConstantTokenTagger;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.RulesetTrainer;
import br.eti.rslemos.brill.RulesetTrainerBehavior;
import br.eti.rslemos.brill.Token;
import br.eti.rslemos.brill.rules.CURWDRule;
import br.eti.rslemos.brill.rules.RuleFactory;

public class TrainerStatsCollectorBehavior {
	@Test
	public void shouldProduce3CURWDRulesForIncompetentBaseTagger() {
		final String FROM_TAG = "TAG";
		
		List<List<Token>> sentences = RulesetTrainerBehavior.buildText_ToSignUp();
		
		List<RuleFactory> ruleFactories = Collections.singletonList(CURWDRule.FACTORY);
		RulesetTrainer trainer = new RulesetTrainer(new ConstantTokenTagger(FROM_TAG), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences, new MyProgressListener()).getRules();
		
		assertEquals(rules.size(), 3);
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TO", "to")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "VB", "sign")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "RP", "up")));
		
		
	}
	
	private static class MyProgressListener implements TrainerProgressListener, ThresholdProgressListener {
		public void trainingStarted() {
			System.out.println("Training Started");
		}

		public void trainingFinished() {
			System.out.println("Training Finished");
		}

		public void improvementOf(int improvement) {
			System.out.printf("improvement of %d\n", improvement);
		}

		public void initialErrorCount(int errorCount) {
			System.out.printf("initial error count set to %d\n", errorCount);
		}
	}
}
