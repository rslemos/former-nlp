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

public class BrillTrainerBehavior {
	@Test
	public void shouldProduceNoRulesForPerfectBaseTagger() {
		List<Sentence> sentences = buildText_ToSignUp();
		
		Map<String, Object> lexicon = new HashMap<String, Object>();
		lexicon.put("to", "TO");
		lexicon.put("sign", "VB");
		lexicon.put("up", "RP");
		
		List<RuleFactory> ruleFactories = Collections.emptyList();
		BrillTrainer trainer = new BrillTrainer(new LookupTokenTagger(lexicon), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences).getRules();
		assertEquals(rules.size(), 0);
	}

	@Test
	public void shouldProduce3CURWDRulesForIncompetentBaseTagger() {
		final Object FROM_TAG = "TAG";
		
		List<Sentence> sentences = buildText_ToSignUp();
		
		List<RuleFactory> ruleFactories = Collections.singletonList(CURWDRule.FACTORY);
		BrillTrainer trainer = new BrillTrainer(new ConstantTokenTagger(FROM_TAG), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences).getRules();
		
		assertEquals(rules.size(), 3);
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TO", "to")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "VB", "sign")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "RP", "up")));
	}
	
	@Test
	public void shouldNeverConsiderTheSameRuleTwiceForTheSameWord() throws Throwable {
		final String WORD1 = "WORD1";
		final String WORD2 = "WORD2";
		final Object TAG = "TAG";
		
		List<Sentence> sentences = buildText(
				buildSentence(
						buildToken(WORD1, TAG), 
						buildToken(WORD2, TAG)
				)
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

			public Collection<Rule> create(Context context, Token target) {
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
		BrillTrainer trainer = new BrillTrainer(new ConstantTokenTagger(null), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences).getRules();
		
		assertEquals(rules.get(0), rule2);
	}

	@Test
	public void shouldConsiderEachAndEverySentenceInCorpus() throws Throwable {
		List<Sentence> sentences = buildText(
				buildSentence(
						buildToken("WORD1", "TAG1"), 
						buildToken("WORD2", "TAG2") 
				),
				buildSentence(
						buildToken("WORD3", "TAG3"), 
						buildToken("WORD4", "TAG4") 
				)
		);
		
		final Object FROM_TAG = "TAG";
		
		List<RuleFactory> ruleFactories = Collections.singletonList(CURWDRule.FACTORY);
		BrillTrainer trainer = new BrillTrainer(new ConstantTokenTagger(FROM_TAG), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences).getRules();
		
		assertEquals(rules.size(), 4);
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TAG1", "WORD1")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TAG2", "WORD2")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TAG3", "WORD3")));
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TAG4", "WORD4")));
	}
	
	@Test
	public void shouldConsiderForNegativeScoreEachAndEverySentenceInCorpus() throws Throwable {
		List<Sentence> sentences = buildText(
				buildSentence(
						buildToken("WORD1", "TAG1"), 
						buildToken("WORD2", "TAG2") 
				),
				buildSentence(
						buildToken("WORD2", "TAG"), 
						buildToken("WORD2", "TAG") 
				)
		);
		
		final Object FROM_TAG = "TAG";
		
		List<RuleFactory> ruleFactories = Collections.singletonList(CURWDRule.FACTORY);
		BrillTrainer trainer = new BrillTrainer(new ConstantTokenTagger(FROM_TAG), ruleFactories);
		
		List<Rule> rules = trainer.train(sentences).getRules();
		
		assertEquals(rules.size(), 1);
		assertTrue(rules.contains(new CURWDRule(FROM_TAG, "TAG1", "WORD1")));
	}
	
	public static List<Sentence> buildText_ToSignUp() {
		return buildText(
				buildSentence(
						buildToken("to", "TO"), 
						buildToken("sign", "VB"), 
						buildToken("up", "RP")
				)
		);
	}

	private static  List<Sentence> buildText(Sentence... sentence) {
		return Arrays.asList(sentence);
	}

	private static  Sentence buildSentence(Token... tokens) {
		return new DefaultSentence(Arrays.asList(tokens));
	}

	private static Token buildToken(String word, Object tag) {
		Token token = new DefaultToken(word);
		token.setTag(tag);
		return token;
	}
}
