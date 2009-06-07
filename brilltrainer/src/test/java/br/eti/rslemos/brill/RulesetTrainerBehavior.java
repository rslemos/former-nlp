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
import br.eti.rslemos.brill.rules.PREV1OR2OR3OR4WDRule;
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
		
		List<RuleFactory> ruleFactories = Collections.singletonList((RuleFactory)CURWDRule.FACTORY);
		RulesetTrainer trainer = new RulesetTrainer(new ConstantTokenTagger(FROM_TAG), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences).getRules();
		
		assertEquals(rules.size(), 3);
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TO", "to")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "VB", "sign")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "RP", "up")));
	}
	
	@Test
	public void shouldNeverConsiderTheSameRuleTwiceForTheSameWord() {
		// [n v-fin PREV1OR2OR3OR4WD null] Context [3, me]: "null/null null/null null/null Ele/pron-pers está/n me/pron-pers municiando/v-ger e/conj-c me/pron-pers"
		List<List<Token>> sentences = buildText(
			buildToken("Ele", "pron-pers"),
			buildToken("está", "v-fin"),
			buildToken("me", "pron-pers"),
			buildToken("municiando", "v-ger"),
			buildToken("e", "conj-c"),
			buildToken("me", "pron-pers")
		);

		List<RuleFactory> ruleFactories = Arrays.asList(CURWDRule.FACTORY, PREV1OR2OR3OR4WDRule.FACTORY1, PREV1OR2OR3OR4WDRule.FACTORY2, PREV1OR2OR3OR4WDRule.FACTORY3, PREV1OR2OR3OR4WDRule.FACTORY4);
		RulesetTrainer trainer = new RulesetTrainer(new ConstantTokenTagger("n"), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences).getRules();
		Rule firstRule = rules.get(0);
		
		assertEquals(firstRule, new CURWDRule("n", "pron-pers", "me"));
	}

	public static List<List<Token>> buildText_ToSignUp() {
		Token to = buildToken("to", "TO");
		Token sign = buildToken("sign", "VB");
		Token up = buildToken("up", "RP");

		return buildText(to, sign, up);
	}

	private static List<List<Token>> buildText(Token... tokens) {
		List<Token> sentence = Arrays.asList(tokens);
		
		return Collections.singletonList(sentence);
	}

	private static Token buildToken(String word, String tag) {
		Token token = new DefaultToken(word);
		token.setTag(tag);
		return token;
	}
}
