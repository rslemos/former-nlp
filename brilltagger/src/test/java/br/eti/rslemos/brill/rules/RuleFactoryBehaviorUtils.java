package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

import org.testng.Assert;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.Token;

public abstract class RuleFactoryBehaviorUtils {

	public static void createAndTest(RuleFactory factory) {
		try {
			createAndTest0(factory);
		} catch (RuleCreationException e) {
			throw new RuntimeException(e);
		}
		
	}

	private static void createAndTest0(RuleFactory factory) throws RuleCreationException {
		Context context = buildContext();
		Rule rule = factory.create(context, context.getToken(0));
		context.reset();
	
		for(int i = 0; i < 3; i++) {
			context.next();
			assertFalse(rule.matches(context));
		}
		
		context.next();
		assertTrue(rule.matches(context));
	
		for(int i = 0; i < 3; i++) {
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
				Assert.assertFalse(rule.hashCode() == model.hashCode(), factory.getClass().getName());
			}
		}

		// explicitly test the surrounding context (and 0-word)
		Context altContext = buildAltContext();
		for (RuleFactory factory : RuleSets.BRILL) {
			Rule rule = factory.create(altContext, altContext.getToken(0));

			String message = factory.getClass().getName();
			
			assertFalse(model.equals(rule), message);
			Assert.assertFalse(rule.hashCode() == model.hashCode(), factory.getClass().getName());
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

}
