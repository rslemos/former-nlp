package br.eti.rslemos.brill;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import br.eti.rslemos.brill.rules.NEXTTAGRuleFactory;
import br.eti.rslemos.brill.rules.PREVTAGRuleFactory;
import br.eti.rslemos.brill.rules.WDPREVTAGRuleFactory;
import br.eti.rslemos.tagger.AbstractToken;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerExampleBehavior {
	
	@Test
	public void exampleMarkHepple2000() {
		Token to = new DefaultToken("to").setFeature(AbstractToken.POS, "TO");
		Token sign = new DefaultToken("sign").setFeature(AbstractToken.POS, "NN");
		Token up = new DefaultToken("up").setFeature(AbstractToken.POS, "RB");
		
		Rule rule1 = PREVTAGRuleFactory.INSTANCE.createRule("NN", "VB", "TO");
		Rule rule2 = WDPREVTAGRuleFactory.INSTANCE.createRule("RB", "RP", "VB", "up");
		
		BrillTagger tagger = new BrillTagger(Arrays.asList(rule1, rule2));
		
		tagger.tag(newDefaultSentence(to, sign, up));
		
		assertEquals(to.getFeature(AbstractToken.POS), "TO");
		assertEquals(sign.getFeature(AbstractToken.POS), "VB");
		assertEquals(up.getFeature(AbstractToken.POS), "RP");
	}

	@Test
	public void example1RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token Chapman = new DefaultToken("Chapman").setFeature(AbstractToken.POS, "NP");
		Token killed = new DefaultToken("killed").setFeature(AbstractToken.POS, "VBN");;
		Token John = new DefaultToken("John").setFeature(AbstractToken.POS, "NP");
		Token Lennon = new DefaultToken("Lennon").setFeature(AbstractToken.POS, "NP");
		
		tagger.tag(newDefaultSentence(Chapman, killed, John, Lennon));

		assertEquals(Chapman.getFeature(AbstractToken.POS), "NP");
		assertEquals(killed.getFeature(AbstractToken.POS), "VBD");
		assertEquals(John.getFeature(AbstractToken.POS), "NP");
		assertEquals(Lennon.getFeature(AbstractToken.POS), "NP");
	}

	@Test
	public void example2RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token John = new DefaultToken("John").setFeature(AbstractToken.POS, "NP");
		Token Lennon = new DefaultToken("Lennon").setFeature(AbstractToken.POS, "NP");
		Token was = new DefaultToken("was").setFeature(AbstractToken.POS, "BEDZ");
		Token shot = new DefaultToken("shot").setFeature(AbstractToken.POS, "VBD");
		Token by = new DefaultToken("by").setFeature(AbstractToken.POS, "BY");
		Token Chapman = new DefaultToken("Chapman").setFeature(AbstractToken.POS, "NP");
		
		tagger.tag(newDefaultSentence(John, Lennon, was, shot, by, Chapman));

		assertEquals(John.getFeature(AbstractToken.POS), "NP");
		assertEquals(Lennon.getFeature(AbstractToken.POS), "NP");
		assertEquals(was.getFeature(AbstractToken.POS), "BEDZ");
		assertEquals(shot.getFeature(AbstractToken.POS), "VBN");
		assertEquals(by.getFeature(AbstractToken.POS), "BY");
		assertEquals(Chapman.getFeature(AbstractToken.POS), "NP");
	}

	@Test
	public void example3RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();

		Token He = new DefaultToken("He").setFeature(AbstractToken.POS, "PPS");
		Token witnessed = new DefaultToken("witnessed").setFeature(AbstractToken.POS, "VBD");
		Token Lennon = new DefaultToken("Lennon").setFeature(AbstractToken.POS, "NP");
		Token killed = new DefaultToken("killed").setFeature(AbstractToken.POS, "VBN");
		Token by = new DefaultToken("by").setFeature(AbstractToken.POS, "BY");
		Token Chapman = new DefaultToken("Chapman").setFeature(AbstractToken.POS, "NP");
		
		tagger.tag(newDefaultSentence(He, witnessed, Lennon, killed, by, Chapman));

		assertEquals(He.getFeature(AbstractToken.POS), "PPS");
		assertEquals(witnessed.getFeature(AbstractToken.POS), "VBD");
		assertEquals(Lennon.getFeature(AbstractToken.POS), "NP");
		assertEquals(killed.getFeature(AbstractToken.POS), "VBN");
		assertEquals(by.getFeature(AbstractToken.POS), "BY");
		assertEquals(Chapman.getFeature(AbstractToken.POS), "NP");
	}

	private static BrillTagger buildRocheAndSchabes1995SampleTagger() {
		Rule rule1 = PREVTAGRuleFactory.INSTANCE.createRule("VBN", "VBD", "NP");
		Rule rule2 = NEXTTAGRuleFactory.INSTANCE.createRule("VBD", "VBN", "BY");
		
		BrillTagger tagger = new BrillTagger(Arrays.asList(rule1, rule2));
		
		return tagger;
	}

	private DefaultSentence newDefaultSentence(Token... tokens) {
		return new DefaultSentence(Arrays.asList(tokens));
	}
}
