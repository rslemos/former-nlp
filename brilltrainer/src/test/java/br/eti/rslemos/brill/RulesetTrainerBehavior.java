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
import br.eti.rslemos.brill.rules.RuleFactory;
import br.eti.rslemos.tagger.ConstantTokenTagger;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.LookupTokenTagger;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public class RulesetTrainerBehavior {
	@Test
	public void shouldProduceNoRulesForPerfectBaseTagger() {
		List<Sentence> sentences = buildText_ToSignUp();
		
		Map<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("to", "TO");
		lexicon.put("sign", "VB");
		lexicon.put("up", "RP");
		
		List<RuleFactory> ruleFactories = Collections.emptyList();
		RulesetTrainer trainer = new RulesetTrainer(new LookupTokenTagger(lexicon), ruleFactories);
		
		Rule[] rules = trainer.train(sentences).getRules();
		assertEquals(rules.length, 0);
	}

	@Test
	public void shouldProduce3CURWDRulesForIncompetentBaseTagger() {
		final String FROM_TAG = "TAG";
		
		List<Sentence> sentences = buildText_ToSignUp();
		
		List<RuleFactory> ruleFactories = Collections.singletonList((RuleFactory)CURWDRule.FACTORY);
		RulesetTrainer trainer = new RulesetTrainer(new ConstantTokenTagger(FROM_TAG), ruleFactories);
		
		List<Rule> rules = Arrays.asList(trainer.train(sentences).getRules());
		
		assertEquals(rules.size(), 3);
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TO", "to")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "VB", "sign")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "RP", "up")));
	}
	
	@Test
	public void shouldNeverConsiderTheSameRuleTwiceForTheSameWord() throws Throwable {
		final String WORD1 = "WORD1";
		final String WORD2 = "WORD2";
		final String TAG = "TAG";
		
		List<Sentence> sentences = buildText(
				buildToken(WORD1, TAG), 
				buildToken(WORD2, TAG)
		);

		class IdentityRule extends AbstractRule {
			private final String name;

			protected IdentityRule(String name) {
				super(null, TAG);
				this.name = name;
			}

			@Override
			public boolean equals(Object o) {
				return this == o;
			}

			@Override
			public String toString() {
				return name;
			}
		}
		
		class WordRule extends IdentityRule {
			private final String word;

			protected WordRule(String name, String word) {
				super(name);
				this.word = word;
			}

			@Override
			public boolean matches(Context context) {
				return (context.getToken(0).getWord() == word) && super.matches(context);
			}
		}
		
		class TheFactory implements RuleFactory {
			private final Rule rule1;
			private final Rule rule2;

			protected TheFactory(Rule rule1, Rule rule2) {
				this.rule1 = rule1;
				this.rule2 = rule2;
			}

			public Rule create(Context context, Token target) {
				String word = target.getWord();
				
				if (word == WORD1)
					return rule1;
				
				if (word == WORD2)
					return rule2;
				
				throw new RuntimeException();
			}
		}
		
		// rule1 will be returned thrice (factory1_a, factory1_b, factory1_c) for token1
		// rule1_x will be returned once (factory1_x) for token1
		// rule2 will be returned one time for each token
		Rule rule1 = new WordRule("rule1", WORD1);
		Rule rule1_a = new WordRule("rule1_a", WORD2);
		Rule rule1_b = new WordRule("rule1_b", WORD2);
		Rule rule1_c = new WordRule("rule1_c", WORD2);
		Rule rule2 = new IdentityRule("rule2");

		RuleFactory factory1_a = new TheFactory(rule1, rule1_a);
		RuleFactory factory1_b = new TheFactory(rule1, rule1_b);
		RuleFactory factory1_c = new TheFactory(rule1, rule1_c);
		RuleFactory factory2 = new TheFactory(rule2, rule2);
		
		List<RuleFactory> ruleFactories = Arrays.asList(factory1_a, factory1_b, factory1_c, factory2);
		RulesetTrainer trainer = new RulesetTrainer(new ConstantTokenTagger(null), ruleFactories);
		
		Rule[] rules = trainer.train(sentences).getRules();
		
		assertEquals(rules[0], rule2);
	}

	public static List<Sentence> buildText_ToSignUp() {
		Token to = buildToken("to", "TO");
		Token sign = buildToken("sign", "VB");
		Token up = buildToken("up", "RP");

		return buildText(to, sign, up);
	}

	private static List<Sentence> buildText(Token... tokens) {
		return Collections.singletonList((Sentence)new DefaultSentence(tokens));
	}

	private static Token buildToken(String word, String tag) {
		Token token = new DefaultToken(word);
		token.setTag(tag);
		return token;
	}
}
