package br.eti.rslemos.brill;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import br.eti.rslemos.brill.rules.NEXTTAGRuleFactory;
import br.eti.rslemos.brill.rules.PREVTAGRuleFactory;
import br.eti.rslemos.brill.rules.WDPREVTAGRuleFactory;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerExampleBehavior {
	
	@Test
	public void exampleMarkHepple2000() {
		Token to = new DefaultToken("to").setTag("TO");
		Token sign = new DefaultToken("sign").setTag("NN");
		Token up = new DefaultToken("up").setTag("RB");
		
		Rule rule1 = PREVTAGRuleFactory.INSTANCE.createRule("NN", "VB", "TO");
		Rule rule2 = WDPREVTAGRuleFactory.INSTANCE.createRule("RB", "RP", "VB", "up");
		
		BrillTagger tagger = new BrillTagger(Arrays.asList(rule1, rule2));
		
		tagger.tag(newDefaultSentence(to, sign, up));
		
		assertEquals(to.getTag(), "TO");
		assertEquals(sign.getTag(), "VB");
		assertEquals(up.getTag(), "RP");
	}

	@Test
	public void example1RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token Chapman = new DefaultToken("Chapman").setTag("NP");
		Token killed = new DefaultToken("killed").setTag("VBN");;
		Token John = new DefaultToken("John").setTag("NP");
		Token Lennon = new DefaultToken("Lennon").setTag("NP");
		
		tagger.tag(newDefaultSentence(Chapman, killed, John, Lennon));

		assertEquals(Chapman.getTag(), "NP");
		assertEquals(killed.getTag(), "VBD");
		assertEquals(John.getTag(), "NP");
		assertEquals(Lennon.getTag(), "NP");
	}

	@Test
	public void example2RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token John = new DefaultToken("John").setTag("NP");
		Token Lennon = new DefaultToken("Lennon").setTag("NP");
		Token was = new DefaultToken("was").setTag("BEDZ");
		Token shot = new DefaultToken("shot").setTag("VBD");
		Token by = new DefaultToken("by").setTag("BY");
		Token Chapman = new DefaultToken("Chapman").setTag("NP");
		
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

		Token He = new DefaultToken("He").setTag("PPS");
		Token witnessed = new DefaultToken("witnessed").setTag("VBD");
		Token Lennon = new DefaultToken("Lennon").setTag("NP");
		Token killed = new DefaultToken("killed").setTag("VBN");
		Token by = new DefaultToken("by").setTag("BY");
		Token Chapman = new DefaultToken("Chapman").setTag("NP");
		
		tagger.tag(newDefaultSentence(He, witnessed, Lennon, killed, by, Chapman));

		assertEquals(He.getTag(), "PPS");
		assertEquals(witnessed.getTag(), "VBD");
		assertEquals(Lennon.getTag(), "NP");
		assertEquals(killed.getTag(), "VBN");
		assertEquals(by.getTag(), "BY");
		assertEquals(Chapman.getTag(), "NP");
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
