package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public abstract class RuleFactoryBehaviorUtils {

	public static void createAndTest(RuleFactory factory) {
		try {
			createAndTest0(factory);
		} catch (RuleCreationException e) {
			throw new RuntimeException(e);
		}
		
	}

	private static void createAndTest0(RuleFactory factory) throws RuleCreationException {
		Context context = RuleContextMother.buildContext();
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

}
