package br.eti.rslemos.brill.tracer;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.RulesetTrainer;
import br.eti.rslemos.brill.Token;
import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;
import br.eti.rslemos.brill.RulesetTrainer.Score;
import br.eti.rslemos.brill.rules.PREVTAGRule;

public abstract aspect BrillDebugger {
	
	/*

	private static final Rule PROBLEM_RULE = new PREV1OR2OR3OR4WDRule("conj-s", "pron-indp", null);
	
	int positive = 0;
	
	after(Context context, Token target) returning(Collection<Rule> rules): 
		call(Collection<Rule> RuleProducingStrategy.produceAllPossibleRules(Context, Token)) && args(context, target) {
		
		Set<Rule> problemRules = new HashSet<Rule>();
		for (Rule rule : rules) {
			if (rule instanceof PREV1OR2OR3OR4WDRule)
				problemRules.add(rule);
		}
		
		for (Rule problemRule : problemRules) {
			reportProblemRule(context, rules, problemRule);
		}
	}

	private void reportProblemRule(Context context, Collection<Rule> rules, Rule problemRule) {
		int thiscount = 0;
		for (Rule rule : rules) {
			if (rule.equals(problemRule)) {
				thiscount++;
			}
		}
		
		if (thiscount > 2) {
			String[] args = new String[9];
			String[] words = new String[9];
			
			for(int i=0; i<args.length; i++) {
				words[i] = context.getToken(i - 4).getWord();
				args[i] = words[i] + "/" + context.getToken(i - 4).getTag();
			}
			
			String dupword = null;
			for(int i=0; i<words.length && dupword == null; i++) {
				if (words[i] != null )
					for(int j=i+1; j<words.length; j++) {
						if (words[i].equals(words[j])) {
							dupword = words[i];
							break;
						}
				}
			}

			if (dupword != null) {
				System.err.printf("[%s] Context [%1d, %s]: ", problemRule, thiscount, dupword);
				System.err.printf("\"%s %s %s %s %s %s %s %s %s\"\n", (Object[])args);
			}
		}
	}
	
	after() returning(Map<Rule, Integer> rules):
		call(Map<Rule, Integer> TrainingContext.produceAllPossibleRules()) {
		System.err.printf("<<< Positive score: %d\n", rules.get(PROBLEM_RULE));
		
	}
*/
	private static final Rule THERULE = new PREVTAGRule("v-fin", "n", "art");
	
	void around(Rule rule): call(void RulesetTrainer.ScoreBoard.addTruePositive(Rule)) && args(rule) {
		if (THERULE.equals(rule))
			proceed(rule);
	}
	
	int positive = 0, negative = 0;
	
	after(Context context, Token token) returning(Collection<Rule> localRules): 
		call(Collection<Rule> RulesetTrainer.RuleProducingStrategy.produceAllPossibleRules(Context, Token)) && args(context, token) {
		if (localRules.contains(THERULE)) {
			String[] args = buildArgsFromContext(context);
			
			System.err.printf("%5d. +MATCH(%s) ", ++positive, args[4]);
			System.err.printf("\"%s %s %s %s %s %s %s %s %s\"\n", (Object[])args);
		}
	}

	after(Score score, Token token, BufferingContext context, Object a, Object b) returning (boolean result):
		call(boolean ObjectUtils.equals(Object, Object)) && args(a, b) &&
		cflow(execution(void BrillScoringStrategy.computeNegativeScore(Score, Token, BufferingContext)) && args(score, token, context)) {
		
		if (result) {
			String[] args = buildArgsFromContext(context);
			
			System.err.printf("%5d. -MATCH(%s [%s]) ", ++negative, args[4], token.getTag());
			System.err.printf("\"%s %s %s %s %s %s %s %s %s\"\n", (Object[])args);
		}
	}

	private int apply;
	
	after(Context context, AbstractRule rule) returning (boolean result):
		call(boolean AbstractRule.matches(Context)) && args(context) &&
		withincode(* AbstractRule.apply(Context)) && this(rule) {
		
		if (result) {
			String[] args = buildArgsFromContext(context);
			
			System.err.printf("%5d. APPLY(%s -> %s) ", ++apply, args[4], rule.getTo());
			System.err.printf("\"%s %s %s %s %s %s %s %s %s\"\n", (Object[])args);
		}
	}
	
	private int errorgroup = 0;
	private int errors = 0;
	
	before(): execution(boolean ThresholdHaltingStrategy.updateAndTest()) {
		errors = 0;
		errorgroup++;
	}
	
	after(List<Token> proofSentence, Context trainingSentence, int errorCount, Object a, Object b) returning (boolean result):
		call(boolean ObjectUtils.equals(Object, Object)) && args(a, b) &&
		cflow(execution(int ThresholdHaltingStrategy.countErrors(List<Token>, Context, int)) && args(proofSentence, trainingSentence, errorCount)) {
		
		if (!result) {
			String[] args = buildArgsFromContext(trainingSentence);
			
			System.err.printf("%02d-%5d. ERROR(%s -> %s) ", errorgroup, ++errors, args[4], String.valueOf(a));
			System.err.printf("\"%s %s %s %s %s %s %s %s %s\"\n", (Object[])args);
			
		}
	}

	private String[] buildArgsFromContext(Context context) {
		String[] args = new String[9];
		String[] words = new String[9];
		
		for(int i=0; i<args.length; i++) {
			words[i] = context.getToken(i - 4).getWord();
			args[i] = words[i] + "/" + context.getToken(i - 4).getTag();
		}
		
		return args;
	}
	
}
