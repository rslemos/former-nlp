package br.eti.rslemos.brill.events;

import java.util.List;

import br.eti.rslemos.brill.BrillScoringStrategy;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;
import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;
import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

public aspect BrillScoringStrategyListenerDispatcher extends BrillScoringStrategyEvents {
	public static BrillScoringStrategyListener listener;
	
	before(BrillScoringStrategy thiz, TrainingContext context): setTrainingContext(thiz, context) {
		listener.beforeSetTrainingContext(thiz, context);
	}
	
	after(BrillScoringStrategy thiz, TrainingContext context): setTrainingContext(thiz, context) {
		listener.afterSetTrainingContext(thiz, context);
	}
	
	before(BrillScoringStrategy thiz, Rule rule): computeRule(thiz, rule) {
		listener.beforeCompute(thiz, rule);
	}
	
	after(BrillScoringStrategy thiz, Rule rule) returning(int result): computeRule(thiz, rule) {
		listener.afterCompute(thiz, rule, result);
	}
	
	before(BrillScoringStrategy thiz, List<Token> proofSentence, BufferingContext trainingSentence):
		computeSentence(thiz, *, proofSentence, trainingSentence) {
		listener.beforeSentence(thiz, proofSentence, trainingSentence);
	}
	
	after(BrillScoringStrategy thiz, List<Token> proofSentence, BufferingContext trainingSentence):
		computeSentence(thiz, *, proofSentence, trainingSentence) {
		listener.afterSentence(thiz, proofSentence, trainingSentence);
		listener.getClass();
	}
	
	public static interface BrillScoringStrategyListener {
		void beforeSetTrainingContext(BrillScoringStrategy strategy, TrainingContext context);
		void afterSetTrainingContext(BrillScoringStrategy strategy, TrainingContext context);
		void beforeCompute(BrillScoringStrategy strategy, Rule rule);
		void afterCompute(BrillScoringStrategy strategy, Rule rule, int result);
		void beforeSentence(BrillScoringStrategy strategy, List<Token> proofSentence, BufferingContext trainingSentence);
		void afterSentence(BrillScoringStrategy strategy, List<Token> proofSentence, BufferingContext trainingSentence);
		void beforeToken(BrillScoringStrategy strategy, Token proofToken, BufferingContext trainingSentence);
		void afterToken(BrillScoringStrategy strategy, Token proofToken, BufferingContext trainingSentence);
	}
}
