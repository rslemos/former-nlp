package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.NEXT1_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.NEXT2_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.NEXT3_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.NEXT4_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.PREV1_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.PREV2_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.PREV3_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.PREV4_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.THIS_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.TO_TAG;
import static br.eti.rslemos.brill.rules.RuleContextMother.buildAltContext;
import static br.eti.rslemos.brill.rules.RuleContextMother.buildContext;
import static br.eti.rslemos.brill.rules.RuleContextMother.getStandardTokens;
import static br.eti.rslemos.brill.rules.RuleContextMother.getUntaggedTokens;
import static br.eti.rslemos.brill.rules.RuleContextMother.skip;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public abstract class RuleBehaviorUtils {

	public static final boolean T = true;
	public static final boolean F = false;
	
	public static void createAndTestMatchability(RuleFactory factory) {
		createAndTestMatchability0(buildContext(getStandardTokens()), factory);
	}

	public static void createAndTestUntaggedMatchability(RuleFactory factory) {
		createAndTestMatchability0(buildContext(getUntaggedTokens()), factory);
	}

	private static void createAndTestMatchability0(Context context, RuleFactory factory) {
		Context context0 = skip(context.clone(), 5);
		
		Collection<Rule> rules = factory.create(context0, context0.getToken(0));
		
		for (Rule rule : rules) {
			testMatchability(context.clone(), rule);
		}
	}

	private static void testMatchability(Context context, Rule rule) {
		for(int i = 0; i < 4; i++) {
			context.next();
			assertFalse(rule.matches(context));
		}
		
		context.next();
		assertTrue(rule.matches(context));

		for(int i = 0; i < 4; i++) {
			context.next();
			assertFalse(rule.matches(context));
		}
	}

	public static void createAndTestObjectSemantics(RuleFactory modelFactory) {
		Context context = buildContext();
		
		Collection<Rule> modelRules = modelFactory.create(context, context.getToken(0));
		
		for (RuleFactory factory : RuleSets.BRILL) {
			Collection<Rule> rules = factory.create(context, context.getToken(0));
		
			String message = factory.getClass().getName();
		
			if (modelFactory.getClass().equals(factory.getClass())) {
				assertTrue(modelRules.containsAll(rules));
				assertTrue(rules.containsAll(modelRules));
		
				for (Rule model : modelRules) {
					for (Rule rule : rules) {
						if (model.equals(rule))
							assertEqualsRules(rule, model);
						else
							assertNotEqualsRules(rule, model, message);
					}
				}
			} else {
				for (Rule model : modelRules) {
					for (Rule rule : rules) {
						assertNotEqualsRules(rule, model, message);
					}
				}
			}
		}
		
		// explicitly test the surrounding context (and 0-word)
		Context altContext = buildAltContext();
		for (RuleFactory factory : RuleSets.BRILL) {
			Collection<Rule> rules = factory.create(altContext, altContext.getToken(0));
			
			String message = factory.getClass().getName();
		
			for (Rule model : modelRules) {
				for (Rule rule : rules) {
					assertNotEqualsRules(rule, model, message);
				}
			}
		}
	}

	private static void assertNotEqualsRules(Rule rule, Rule model, String message) {
		assertFalse(message, model.equals(rule));
		assertFalse(message, rule.hashCode() == model.hashCode());
	}

	private static void assertEqualsRules(Rule rule, Rule model) {
		assertTrue(model.equals(rule));
		assertEquals(rule.hashCode(), model.hashCode());
	}

	public static void createAndTestBrillString(RuleFactory factory, String... expected) {
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn(TO_TAG);
		
		Context context = buildContext();
		
		Collection<Rule> rules = factory.create(context, token);
		Set<String> actual = new TreeSet<String>();
		for (Rule rule : rules) {
			actual.add(((AbstractRule)rule).toString());
		}
		
		assertEquals(actual, new TreeSet<String>(Arrays.asList(expected)));
	}

	public static void createAndTestBasicDependency(RuleFactory factory) {
		Context context = buildContext();
		Collection<Rule> rules = factory.create(context, context.getToken(0));
		
		for (Rule rule : rules) {
			assertTrue(rule.firingDependsOnObject(THIS_TAG));
			assertFalse(rule.firingDependsOnObject(TO_TAG));
		}
	}

	public static void testDependency(Rule rule, boolean... dependencies) {
		assertEquals(rule.firingDependsOnObject(PREV4_TAG), dependencies[0]);
		assertEquals(rule.firingDependsOnObject(PREV3_TAG), dependencies[1]);
		assertEquals(rule.firingDependsOnObject(PREV2_TAG), dependencies[2]);
		assertEquals(rule.firingDependsOnObject(PREV1_TAG), dependencies[3]);
		assertEquals(rule.firingDependsOnObject(THIS_TAG), true);
		assertEquals(rule.firingDependsOnObject(NEXT1_TAG), dependencies[4]);
		assertEquals(rule.firingDependsOnObject(NEXT2_TAG), dependencies[5]);
		assertEquals(rule.firingDependsOnObject(NEXT3_TAG), dependencies[6]);
		assertEquals(rule.firingDependsOnObject(NEXT4_TAG), dependencies[7]);
	}

}
