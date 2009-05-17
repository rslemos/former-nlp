package br.eti.rslemos.brill;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.rules.CURWDRule;

public class RulesetTrainerBehavior {
	@Test
	public void shouldProduceNoRulesForPerfectBaseTagger() {
		List<List<Token>> sentences = buildText_ToSignUp();
		
		Map<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("to", "TO");
		lexicon.put("sign", "VB");
		lexicon.put("up", "RP");
		
		RulesetTrainer trainer = new RulesetTrainer(new LookupBaseTagger(lexicon));
		
		List<Rule> rules = trainer.train(sentences);
		assertEquals(rules.size(), 0);
	}


	@Test
	public void shouldProduce3CURWDRulesForIncompetentBaseTagger() {
		final String FROM_TAG = "TAG";
		
		List<List<Token>> sentences = buildText_ToSignUp();
		
		RulesetTrainer trainer = new RulesetTrainer(new ConstantBaseTagger(FROM_TAG));
		
		List<Rule> rules = trainer.train(sentences);
		System.out.printf("%s\n", rules);
		
		assertEquals(rules.size(), 3);
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TO", "to")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "VB", "sign")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "RP", "up")));
	}

	private List<List<Token>> buildText_ToSignUp() {
		Token to = mock(Token.class);
		when(to.getWord()).thenReturn("to");
		when(to.getTag()).thenReturn("TO");
		
		Token sign = mock(Token.class);
		when(sign.getWord()).thenReturn("sign");
		when(sign.getTag()).thenReturn("VB");

		Token up = mock(Token.class);
		when(up.getWord()).thenReturn("up");
		when(up.getTag()).thenReturn("RP");

		List<Token> sentence = Arrays.asList(to, sign, up);
		return Collections.singletonList(sentence);
	}
}
