package br.eti.rslemos.brill;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.rules.CURWDRule;
import br.eti.rslemos.brill.rules.PREVTAGRule;
import br.eti.rslemos.brill.rules.RuleFactory;

public class RulesetTrainerBehavior {
	@Test
	public void shouldProduceNoRulesForPerfectBaseTagger() {
		List<List<Token>> sentences = buildText_ToSignUp();
		
		Map<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("to", "TO");
		lexicon.put("sign", "VB");
		lexicon.put("up", "RP");
		
		List<RuleFactory> ruleFactories = Collections.emptyList();
		RulesetTrainer trainer = new RulesetTrainer(new LookupTokenTagger(lexicon), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences).getRules();
		assertEquals(rules.size(), 0);
	}

	@Test
	public void shouldProduce3CURWDRulesForIncompetentBaseTagger() {
		final String FROM_TAG = "TAG";
		
		List<List<Token>> sentences = buildText_ToSignUp();
		
		List<RuleFactory> ruleFactories = Collections.singletonList(CURWDRule.FACTORY);
		RulesetTrainer trainer = new RulesetTrainer(new ConstantTokenTagger(FROM_TAG), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences).getRules();
		
		assertEquals(rules.size(), 3);
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TO", "to")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "VB", "sign")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "RP", "up")));
	}

	@Test
	public void shouldProduce2RulesForInnacurateBaseTagger() {
		List<List<Token>> sentences = buildText_ToSignUp();
		
		Map<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("to", "TO");
		lexicon.put("sign", "NN");
		lexicon.put("up", "RB");
		
		List<RuleFactory> ruleFactories = Arrays.asList(PREVTAGRule.FACTORY);
		RulesetTrainer trainer = new RulesetTrainer(new LookupTokenTagger(lexicon), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences).getRules();
		
		assertEquals(rules.size(), 2);
		assertTrue(rules.contains(new PREVTAGRule("NN", "VB", "TO")));
		assertTrue(rules.contains(new PREVTAGRule("RB", "RP", "VB")));
	}

	private List<List<Token>> buildText_ToSignUp() {
		Token to = new DefaultToken("to");
		to.setTag("TO");
		
		Token sign = new DefaultToken("sign");
		sign.setTag("VB");

		Token up = new DefaultToken("up");
		up.setTag("RP");

		List<Token> sentence = Arrays.asList(to, sign, up);
		return Collections.singletonList(sentence);
	}
}
