package br.eti.rslemos.brill;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
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

@SuppressWarnings("unchecked")
public class RulesetTrainerBehavior {
	@Test
	public void shouldProduceNoRulesForPerfectBaseTagger() {
		List<Sentence<String>> sentences = buildText_ToSignUp();
		
		Map<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("to", "TO");
		lexicon.put("sign", "VB");
		lexicon.put("up", "RP");
		
		List<RuleFactory<String>> ruleFactories = Collections.emptyList();
		RulesetTrainer<String> trainer = new RulesetTrainer<String>(new LookupTokenTagger<String>(lexicon), ruleFactories);
		
		Rule<String>[] rules = trainer.train(sentences).getRules();
		assertEquals(rules.length, 0);
	}

	@Test
	public void shouldProduce3CURWDRulesForIncompetentBaseTagger() {
		final String FROM_TAG = "TAG";
		
		List<Sentence<String>> sentences = buildText_ToSignUp();
		
		List<RuleFactory<String>> ruleFactories = Collections.singletonList((RuleFactory<String>)CURWDRule.<String>FACTORY());
		RulesetTrainer<String> trainer = new RulesetTrainer<String>(new ConstantTokenTagger<String>(FROM_TAG), ruleFactories);
		
		List<Rule<String>> rules = Arrays.asList(trainer.train(sentences).getRules());
		
		assertEquals(rules.size(), 3);
		assertTrue(rules.contains(new CURWDRule<String>(FROM_TAG, "TO", "to")));
		assertTrue(rules.contains(new CURWDRule<String>(FROM_TAG, "VB", "sign")));
		assertTrue(rules.contains(new CURWDRule<String>(FROM_TAG, "RP", "up")));
	}
	
	@Test
	public void shouldNeverConsiderTheSameRuleTwiceForTheSameWord() throws Throwable {
		final String WORD1 = "WORD1";
		final String WORD2 = "WORD2";
		final String TAG = "TAG";
		
		List<Sentence<String>> sentences = buildText(
				buildToken(WORD1, TAG), 
				buildToken(WORD2, TAG)
		);

		class IdentityRule extends AbstractRule<String> {
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
			public boolean matches(Context<String> context) {
				return (context.getToken(0).getWord() == word) && super.matches(context);
			}
		}
		
		class TheFactory implements RuleFactory<String> {
			private final Rule<String> rule1;
			private final Rule<String> rule2;

			protected TheFactory(Rule<String> rule1, Rule<String> rule2) {
				this.rule1 = rule1;
				this.rule2 = rule2;
			}

			public Collection<Rule<String>> create(Context<String> context, Token<String> target) {
				String word = target.getWord();
				
				if (word == WORD1)
					return Collections.singleton(rule1);
				
				if (word == WORD2)
					return Collections.singleton(rule2);
				
				throw new RuntimeException();
			}
		}
		
		// rule1 will be returned thrice (factory1_a, factory1_b, factory1_c) for token1
		// rule1_x will be returned once (factory1_x) for token1
		// rule2 will be returned one time for each token
		Rule<String> rule1 = new WordRule("rule1", WORD1);
		Rule<String> rule1_a = new WordRule("rule1_a", WORD2);
		Rule<String> rule1_b = new WordRule("rule1_b", WORD2);
		Rule<String> rule1_c = new WordRule("rule1_c", WORD2);
		Rule<String> rule2 = new IdentityRule("rule2");

		RuleFactory<String> factory1_a = new TheFactory(rule1, rule1_a);
		RuleFactory<String> factory1_b = new TheFactory(rule1, rule1_b);
		RuleFactory<String> factory1_c = new TheFactory(rule1, rule1_c);
		RuleFactory<String> factory2 = new TheFactory(rule2, rule2);
		
		List<RuleFactory<String>> ruleFactories = Arrays.asList(factory1_a, factory1_b, factory1_c, factory2);
		RulesetTrainer<String> trainer = new RulesetTrainer<String>(new ConstantTokenTagger<String>(null), ruleFactories);
		
		Rule<String>[] rules = trainer.train(sentences).getRules();
		
		assertEquals(rules[0], rule2);
	}

	public static List<Sentence<String>> buildText_ToSignUp() {
		Token<String> to = buildToken("to", "TO");
		Token<String> sign = buildToken("sign", "VB");
		Token<String> up = buildToken("up", "RP");

		return buildText(to, sign, up);
	}

	private static List<Sentence<String>> buildText(Token<String>... tokens) {
		return Collections.singletonList((Sentence<String>)new DefaultSentence<String>(tokens));
	}

	private static Token<String> buildToken(String word, String tag) {
		Token<String> token = new DefaultToken<String>(word);
		token.setTag(tag);
		return token;
	}
}
