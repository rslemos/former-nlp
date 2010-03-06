package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

@SuppressWarnings("unchecked")
public abstract class RuleBehaviorUtils {

	public static final boolean T = true;
	public static final boolean F = false;
	
	public static void createAndTestMatchability(RuleFactory<String> factory) {
		createAndTestMatchability0(buildContext(getStandardTokens()), factory);
	}

	public static void createAndTestUntaggedMatchability(RuleFactory<String> factory) {
		createAndTestMatchability0(buildContext(getUntaggedTokens()), factory);
	}

	private static void createAndTestMatchability0(Context<String> context, RuleFactory<String> factory) {
		Context<String> context0 = skip(context.clone(), 5);
		
		Collection<Rule<String>> rules = factory.create(context0, context0.getToken(0));
		
		for (Rule<String> rule : rules) {
			testMatchability(context.clone(), rule);
		}
	}

	private static void testMatchability(Context<String> context, Rule<String> rule) {
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

	public static void createAndTestObjectSemantics(RuleFactory<String> modelFactory) {
		Context<String> context = buildContext();
		
		Collection<Rule<String>> modelRules = modelFactory.create(context, context.getToken(0));
		
		for (RuleFactory<String> factory : RuleSets.BRILL) {
			Collection<Rule<String>> rules = factory.create(context, context.getToken(0));
		
			String message = factory.getClass().getName();
		
			if (modelFactory.getClass().equals(factory.getClass())) {
				assertTrue(modelRules.containsAll(rules));
				assertTrue(rules.containsAll(modelRules));
		
				for (Rule<String> model : modelRules) {
					for (Rule<String> rule : rules) {
						if (model.equals(rule))
							assertEqualsRules(rule, model);
						else
							assertNotEqualsRules(rule, model, message);
					}
				}
			} else {
				for (Rule<String> model : modelRules) {
					for (Rule<String> rule : rules) {
						assertNotEqualsRules(rule, model, message);
					}
				}
			}
		}
		
		// explicitly test the surrounding context (and 0-word)
		Context<String> altContext = buildAltContext();
		for (RuleFactory<String> factory : RuleSets.BRILL) {
			Collection<Rule<String>> rules = factory.create(altContext, altContext.getToken(0));
			
			String message = factory.getClass().getName();
		
			for (Rule<String> model : modelRules) {
				for (Rule<String> rule : rules) {
					assertNotEqualsRules(rule, model, message);
				}
			}
		}
	}

	private static void assertNotEqualsRules(Rule<?> rule, Rule<?> model, String message) {
		assertFalse(model.equals(rule), message);
		assertFalse(rule.hashCode() == model.hashCode(), message);
	}

	private static void assertEqualsRules(Rule<?> rule, Rule<?> model) {
		assertTrue(model.equals(rule));
		assertEquals(rule.hashCode(), model.hashCode());
	}

	public static void createAndTestBrillText(RuleFactory<String> factory, String expected) {
		Token<String> token = mock(Token.class);
		when(token.getTag()).thenReturn(TO_TAG);
		
		Context<String> context = buildContext();
		
		Collection<Rule<String>> rules = factory.create(context, token);
		for (Rule<String> rule : rules) {
			assertEquals(((SerializableAsBrillText)rule).toBrillText(), expected);	
		}
	}

	public static void createAndTestBasicDependency(RuleFactory<String> factory) {
		Context<String> context = buildContext();
		Collection<Rule<String>> rules = factory.create(context, context.getToken(0));
		
		for (Rule<String> rule : rules) {
			assertTrue(rule.firingDependsOnTag(THIS_TAG));
			assertFalse(rule.firingDependsOnTag(TO_TAG));
		}
	}

	public static void testDependency(Rule<String> rule, boolean... dependencies) {
		assertEquals(rule.firingDependsOnTag(PREV4_TAG), dependencies[0]);
		assertEquals(rule.firingDependsOnTag(PREV3_TAG), dependencies[1]);
		assertEquals(rule.firingDependsOnTag(PREV2_TAG), dependencies[2]);
		assertEquals(rule.firingDependsOnTag(PREV1_TAG), dependencies[3]);
		assertEquals(rule.firingDependsOnTag(THIS_TAG), true);
		assertEquals(rule.firingDependsOnTag(NEXT1_TAG), dependencies[4]);
		assertEquals(rule.firingDependsOnTag(NEXT2_TAG), dependencies[5]);
		assertEquals(rule.firingDependsOnTag(NEXT3_TAG), dependencies[6]);
		assertEquals(rule.firingDependsOnTag(NEXT4_TAG), dependencies[7]);
	}

}
