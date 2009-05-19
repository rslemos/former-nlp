package br.eti.rslemos.brill;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.rules.NEXTTAGRule;
import br.eti.rslemos.brill.rules.PREVTAGRule;
import br.eti.rslemos.brill.rules.WDPREVTAGRule;

public class RuleBasedTaggerExampleBehavior {
	
	@Test
	public void exampleMarkHepple2000() {
		Token to = new DefaultToken("to");
		Token sign = new DefaultToken("sign");
		Token up = new DefaultToken("up");
		
		Map<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("to", "TO");
		lexicon.put("sign", "NN");
		lexicon.put("up", "RB");
		
		Tagger baseTagger = new LookupTokenTagger(lexicon);
		
		Rule rule1 = new PREVTAGRule("NN", "VB", "TO");
		Rule rule2 = new WDPREVTAGRule("RB", "RP", "VB", "up");
		
		RuleBasedTagger tagger = new RuleBasedTagger(baseTagger, Arrays.asList(rule1, rule2));
		
		tagger.tagSentence(Arrays.asList(to, sign, up));
		
		assertEquals(to.getTag(), "TO");
		assertEquals(sign.getTag(), "VB");
		assertEquals(up.getTag(), "RP");
	}

	@Test
	public void example1RocheAndSchabes1995() {
		RuleBasedTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token Chapman = new DefaultToken("Chapman");
		Token killed = new DefaultToken("killed");
		Token John = new DefaultToken("John");
		Token Lennon = new DefaultToken("Lennon");
		
		tagger.tagSentence(Arrays.asList(Chapman, killed, John, Lennon));

		assertEquals(Chapman.getTag(), "NP");
		assertEquals(killed.getTag(), "VBD");
		assertEquals(John.getTag(), "NP");
		assertEquals(Lennon.getTag(), "NP");
	}

	@Test
	public void example2RocheAndSchabes1995() {
		RuleBasedTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token John = new DefaultToken("John");
		Token Lennon = new DefaultToken("Lennon");
		Token was = new DefaultToken("was");
		Token shot = new DefaultToken("shot");
		Token by = new DefaultToken("by");
		Token Chapman = new DefaultToken("Chapman");
		
		tagger.tagSentence(Arrays.asList(John, Lennon, was, shot, by, Chapman));

		assertEquals(John.getTag(), "NP");
		assertEquals(Lennon.getTag(), "NP");
		assertEquals(was.getTag(), "BEDZ");
		assertEquals(shot.getTag(), "VBN");
		assertEquals(by.getTag(), "BY");
		assertEquals(Chapman.getTag(), "NP");
	}

	@Test
	public void example3RocheAndSchabes1995() {
		RuleBasedTagger tagger = buildRocheAndSchabes1995SampleTagger();

		Token He = new DefaultToken("He");
		Token witnessed = new DefaultToken("witnessed");
		Token Lennon = new DefaultToken("Lennon");
		Token killed = new DefaultToken("killed");
		Token by = new DefaultToken("by");
		Token Chapman = new DefaultToken("Chapman");
		
		tagger.tagSentence(Arrays.asList(He, witnessed, Lennon, killed, by, Chapman));

		assertEquals(He.getTag(), "PPS");
		assertEquals(witnessed.getTag(), "VBD");
		assertEquals(Lennon.getTag(), "NP");
		assertEquals(killed.getTag(), "VBN");
		assertEquals(by.getTag(), "BY");
		assertEquals(Chapman.getTag(), "NP");
	}

	private static RuleBasedTagger buildRocheAndSchabes1995SampleTagger() {
		Map<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("killed", "VBN");
		lexicon.put("was", "BEDZ");
		lexicon.put("shot", "VBD");
		lexicon.put("by", "BY");
		lexicon.put("He", "PPS");
		lexicon.put("witnessed", "VBD");
		
		Tagger tagger1 = new LookupTokenTagger(lexicon);
		Tagger tagger2 = new ConstantTokenTagger("NP");
		List<Tagger> taggers = Arrays.asList(tagger1, tagger2);
		Tagger baseTagger = new CompositeTagger(taggers);
		
		Rule rule1 = new PREVTAGRule("VBN", "VBD", "NP");
		Rule rule2 = new NEXTTAGRule("VBD", "VBN", "BY");
		List<Rule> rules = Arrays.asList(rule1, rule2);
		
		RuleBasedTagger tagger = new RuleBasedTagger(baseTagger, rules);
		
		return tagger;
	}
}