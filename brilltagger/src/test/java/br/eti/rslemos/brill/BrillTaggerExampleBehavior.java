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
import br.eti.rslemos.tagger.DefaultTag;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.LookupTokenTagger;
import br.eti.rslemos.tagger.Tag;
import br.eti.rslemos.tagger.Tagger;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerExampleBehavior {
	
	@Test
	public void exampleMarkHepple2000() {
		Token to = new DefaultToken("to");
		Token sign = new DefaultToken("sign");
		Token up = new DefaultToken("up");
		
		Map<String, Tag> lexicon = new HashMap<String, Tag>();
		lexicon.put("to", new DefaultTag("TO"));
		lexicon.put("sign", new DefaultTag("NN"));
		lexicon.put("up", new DefaultTag("RB"));
		
		Tagger baseTagger = new LookupTokenTagger(lexicon);
		
		Rule rule1 = new PREVTAGRule(new DefaultTag("NN"), new DefaultTag("VB"), new DefaultTag("TO"));
		Rule rule2 = new WDPREVTAGRule(new DefaultTag("RB"), new DefaultTag("RP"), new DefaultTag("VB"), "up");
		
		BrillTagger tagger = new BrillTagger(baseTagger, Arrays.asList(rule1, rule2));
		
		tagger.tag(newDefaultSentence(to, sign, up));
		
		assertEquals(to.getTag(), new DefaultTag("TO"));
		assertEquals(sign.getTag(), new DefaultTag("VB"));
		assertEquals(up.getTag(), new DefaultTag("RP"));
	}

	@Test
	public void example1RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token Chapman = new DefaultToken("Chapman");
		Token killed = new DefaultToken("killed");
		Token John = new DefaultToken("John");
		Token Lennon = new DefaultToken("Lennon");
		
		tagger.tag(newDefaultSentence(Chapman, killed, John, Lennon));

		assertEquals(Chapman.getTag(), new DefaultTag("NP"));
		assertEquals(killed.getTag(), new DefaultTag("VBD"));
		assertEquals(John.getTag(), new DefaultTag("NP"));
		assertEquals(Lennon.getTag(), new DefaultTag("NP"));
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

		assertEquals(John.getTag(), new DefaultTag("NP"));
		assertEquals(Lennon.getTag(), new DefaultTag("NP"));
		assertEquals(was.getTag(), new DefaultTag("BEDZ"));
		assertEquals(shot.getTag(), new DefaultTag("VBN"));
		assertEquals(by.getTag(), new DefaultTag("BY"));
		assertEquals(Chapman.getTag(), new DefaultTag("NP"));
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

		assertEquals(He.getTag(), new DefaultTag("PPS"));
		assertEquals(witnessed.getTag(), new DefaultTag("VBD"));
		assertEquals(Lennon.getTag(), new DefaultTag("NP"));
		assertEquals(killed.getTag(), new DefaultTag("VBN"));
		assertEquals(by.getTag(), new DefaultTag("BY"));
		assertEquals(Chapman.getTag(), new DefaultTag("NP"));
	}

	private static BrillTagger buildRocheAndSchabes1995SampleTagger() {
		Map<String, Tag> lexicon = new HashMap<String, Tag>();
		lexicon.put("killed", new DefaultTag("VBN"));
		lexicon.put("was", new DefaultTag("BEDZ"));
		lexicon.put("shot", new DefaultTag("VBD"));
		lexicon.put("by", new DefaultTag("BY"));
		lexicon.put("He", new DefaultTag("PPS"));
		lexicon.put("witnessed", new DefaultTag("VBD"));
		
		Tagger tagger1 = new LookupTokenTagger(lexicon);
		Tagger tagger2 = new ConstantTokenTagger(new DefaultTag("NP"));
		Tagger baseTagger = new CompositeTagger(tagger1, tagger2);
		
		Rule rule1 = new PREVTAGRule(new DefaultTag("VBN"), new DefaultTag("VBD"), new DefaultTag("NP"));
		Rule rule2 = new NEXTTAGRule(new DefaultTag("VBD"), new DefaultTag("VBN"), new DefaultTag("BY"));
		
		BrillTagger tagger = new BrillTagger(baseTagger, Arrays.asList(rule1, rule2));
		
		return tagger;
	}

	private DefaultSentence newDefaultSentence(Token... tokens) {
		return new DefaultSentence(Arrays.asList(tokens));
	}
}
