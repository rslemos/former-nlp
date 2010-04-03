package br.eti.rslemos.brill;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.rules.NEXTTAGRule;
import br.eti.rslemos.brill.rules.PREVTAGRule;
import br.eti.rslemos.brill.rules.WDPREVTAGRule;
import br.eti.rslemos.tagger.CompositeTagger;
import br.eti.rslemos.tagger.ConstantTokenTagger;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.LookupTokenTagger;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerExampleBehavior {
	
	@Test
	public void exampleMarkHepple2000() {
		Token to = new DefaultToken("to");
		Token sign = new DefaultToken("sign");
		Token up = new DefaultToken("up");
		
		Map<String, Object> lexicon = new HashMap<String, Object>();
		lexicon.put("to", "TO");
		lexicon.put("sign", "NN");
		lexicon.put("up", "RB");
		
		Tagger baseTagger = new LookupTokenTagger(lexicon);
		
		Rule rule1 = new PREVTAGRule("NN", "VB", "TO");
		Rule rule2 = new WDPREVTAGRule("RB", "RP", "VB", "up");
		
		BrillTagger tagger = new BrillTagger(baseTagger, Arrays.asList(rule1, rule2));
		
		tagger.tag(newDefaultSentence(to, sign, up));
		
		assertEquals(to.getTag(), "TO");
		assertEquals(sign.getTag(), "VB");
		assertEquals(up.getTag(), "RP");
	}

	@Test
	public void example1RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token Chapman = new DefaultToken("Chapman");
		Token killed = new DefaultToken("killed");
		Token John = new DefaultToken("John");
		Token Lennon = new DefaultToken("Lennon");
		
		tagger.tag(newDefaultSentence(Chapman, killed, John, Lennon));

		assertEquals(Chapman.getTag(), "NP");
		assertEquals(killed.getTag(), "VBD");
		assertEquals(John.getTag(), "NP");
		assertEquals(Lennon.getTag(), "NP");
	}

	@Test
	public void example2RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token John = new DefaultToken("John");
		Token Lennon = new DefaultToken("Lennon");
		Token was = new DefaultToken("was");
		Token shot = new DefaultToken("shot");
		Token by = new DefaultToken("by");
		Token Chapman = new DefaultToken("Chapman");
		
		tagger.tag(newDefaultSentence(John, Lennon, was, shot, by, Chapman));

		assertEquals(John.getTag(), "NP");
		assertEquals(Lennon.getTag(), "NP");
		assertEquals(was.getTag(), "BEDZ");
		assertEquals(shot.getTag(), "VBN");
		assertEquals(by.getTag(), "BY");
		assertEquals(Chapman.getTag(), "NP");
	}

	@Test
	public void example3RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();

		Token He = new DefaultToken("He");
		Token witnessed = new DefaultToken("witnessed");
		Token Lennon = new DefaultToken("Lennon");
		Token killed = new DefaultToken("killed");
		Token by = new DefaultToken("by");
		Token Chapman = new DefaultToken("Chapman");
		
		tagger.tag(newDefaultSentence(He, witnessed, Lennon, killed, by, Chapman));

		assertEquals(He.getTag(), "PPS");
		assertEquals(witnessed.getTag(), "VBD");
		assertEquals(Lennon.getTag(), "NP");
		assertEquals(killed.getTag(), "VBN");
		assertEquals(by.getTag(), "BY");
		assertEquals(Chapman.getTag(), "NP");
	}

	private static BrillTagger buildRocheAndSchabes1995SampleTagger() {
		Map<String, Object> lexicon = new HashMap<String, Object>();
		lexicon.put("killed", "VBN");
		lexicon.put("was", "BEDZ");
		lexicon.put("shot", "VBD");
		lexicon.put("by", "BY");
		lexicon.put("He", "PPS");
		lexicon.put("witnessed", "VBD");
		
		Tagger tagger1 = new LookupTokenTagger(lexicon);
		Tagger tagger2 = new ConstantTokenTagger("NP");
		Tagger baseTagger = new CompositeTagger(tagger1, tagger2);
		
		Rule rule1 = new PREVTAGRule("VBN", "VBD", "NP");
		Rule rule2 = new NEXTTAGRule("VBD", "VBN", "BY");
		
		BrillTagger tagger = new BrillTagger(baseTagger, Arrays.asList(rule1, rule2));
		
		return tagger;
	}

	private DefaultSentence newDefaultSentence(Token... tokens) {
		return new DefaultSentence(Arrays.asList(tokens));
	}
}
