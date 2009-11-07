package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;

public abstract class RuleBehaviorUtils {

	public static final boolean T = true;
	public static final boolean F = false;
	
	public static void createAndTestMatchability(RuleFactory factory) {
		try {
			createAndTestMatchability0(buildContext(getStandardTokens()), factory);
		} catch (RuleCreationException e) {
			throw new RuntimeException(e);
		}
	}

	public static void createAndTestUntaggedMatchability(RuleFactory factory) {
		try {
			createAndTestMatchability0(buildContext(getUntaggedTokens()), factory);
		} catch (RuleCreationException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createAndTestMatchability0(Context context, RuleFactory factory) throws RuleCreationException {
		Context context0 = skip(context.clone(), 5);
		Rule rule = factory.create(context0, context0.getToken(0));
		
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
		try {
			createAndTestObjectSemantics0(modelFactory);
		} catch (RuleCreationException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createAndTestObjectSemantics0(RuleFactory modelFactory) throws RuleCreationException {
		// great care was exercised to avoid hash colision in cases where rules were different;
		// hashCode may eventually fail depending on hash of individual strings used;
		// once the string-set is settled, the test will work, since String.hashCode() is not random.
		
		Context context = buildContext();

		Rule model = modelFactory.create(context, context.getToken(0));
	
		for (RuleFactory factory : RuleSets.BRILL) {
			Rule rule = factory.create(context, context.getToken(0));
			if (modelFactory == factory) {
				assertTrue(model.equals(rule));
				assertEquals(rule.hashCode(), model.hashCode());
			} else {
				String message = factory.getClass().getName();
				
				assertFalse(model.equals(rule), message);
				assertFalse(rule.hashCode() == model.hashCode(), factory.getClass().getName());
			}
		}

		// explicitly test the surrounding context (and 0-word)
		Context altContext = buildAltContext();
		for (RuleFactory factory : RuleSets.BRILL) {
			Rule rule = factory.create(altContext, altContext.getToken(0));

			String message = factory.getClass().getName();
			
			assertFalse(model.equals(rule), message);
			assertFalse(rule.hashCode() == model.hashCode(), factory.getClass().getName());
		}

	}

	public static void createAndTestBrillText(RuleFactory factory, String expected) {
		try {
			createAndTestBrillText0(factory, expected);
		} catch (RuleCreationException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createAndTestBrillText0(RuleFactory factory, String expected) throws RuleCreationException {
		Token token = mock(Token.class);
		when(token.getTag()).thenReturn(TO_TAG);
		
		Context context = buildContext();
		
		SerializableAsBrillText rule = (SerializableAsBrillText) factory.create(context, token);
		assertEquals(rule.toBrillText(), expected);
	}

	public static void createAndTestBasicDependency(RuleFactory factory) {
		try {
			createAndTestBasicDependency0(buildContext(), factory);
		} catch (RuleCreationException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createAndTestBasicDependency0(Context context, RuleFactory factory) throws RuleCreationException {
		Rule rule = factory.create(context, context.getToken(0));
	
		assertTrue(rule.firingDependsOnTag(THIS_TAG));
		assertFalse(rule.firingDependsOnTag(TO_TAG));
	}

	public static void createAndTestContextIndependency(RuleFactory factory) {
		createAndTestContextDependency(factory, F, F, F, F, F, F, F, F);
	}

	public static void createAndTestContextDependency(RuleFactory factory, boolean... dependencies) {
		try {
			createAndTestContextDependency0(buildContext(), factory, dependencies);
		} catch (RuleCreationException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createAndTestContextDependency0(Context context, RuleFactory factory, boolean... dependencies) throws RuleCreationException {
		Rule rule = factory.create(context, context.getToken(0));
	
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
