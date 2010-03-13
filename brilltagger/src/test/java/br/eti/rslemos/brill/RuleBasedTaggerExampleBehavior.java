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

@SuppressWarnings("unchecked")
public class RuleBasedTaggerExampleBehavior {
	
	@Test
	public void exampleMarkHepple2000() {
		Token<String> to = new DefaultToken<String>("to");
		Token<String> sign = new DefaultToken<String>("sign");
		Token<String> up = new DefaultToken<String>("up");
		
		Map<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("to", "TO");
		lexicon.put("sign", "NN");
		lexicon.put("up", "RB");
		
		Tagger<String> baseTagger = new LookupTokenTagger<String>(lexicon);
		
		Rule<String> rule1 = new PREVTAGRule<String>("NN", "VB", "TO");
		Rule<String> rule2 = new WDPREVTAGRule<String>("RB", "RP", "VB", "up");
		
		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>(baseTagger, Arrays.asList(rule1, rule2));
		
		tagger.tag(new DefaultSentence<String>(to, sign, up));
		
		assertEquals(to.getTag(), "TO");
		assertEquals(sign.getTag(), "VB");
		assertEquals(up.getTag(), "RP");
	}

	@Test
	public void example1RocheAndSchabes1995() {
		RuleBasedTagger<String> tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token<String> Chapman = new DefaultToken<String>("Chapman");
		Token<String> killed = new DefaultToken<String>("killed");
		Token<String> John = new DefaultToken<String>("John");
		Token<String> Lennon = new DefaultToken<String>("Lennon");
		
		tagger.tag(new DefaultSentence<String>(Chapman, killed, John, Lennon));

		assertEquals(Chapman.getTag(), "NP");
		assertEquals(killed.getTag(), "VBD");
		assertEquals(John.getTag(), "NP");
		assertEquals(Lennon.getTag(), "NP");
	}

	@Test
	public void example2RocheAndSchabes1995() {
		RuleBasedTagger<String> tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token<String> John = new DefaultToken<String>("John");
		Token<String> Lennon = new DefaultToken<String>("Lennon");
		Token<String> was = new DefaultToken<String>("was");
		Token<String> shot = new DefaultToken<String>("shot");
		Token<String> by = new DefaultToken<String>("by");
		Token<String> Chapman = new DefaultToken<String>("Chapman");
		
		tagger.tag(new DefaultSentence<String>(John, Lennon, was, shot, by, Chapman));

		assertEquals(John.getTag(), "NP");
		assertEquals(Lennon.getTag(), "NP");
		assertEquals(was.getTag(), "BEDZ");
		assertEquals(shot.getTag(), "VBN");
		assertEquals(by.getTag(), "BY");
		assertEquals(Chapman.getTag(), "NP");
	}

	@Test
	public void example3RocheAndSchabes1995() {
		RuleBasedTagger<String> tagger = buildRocheAndSchabes1995SampleTagger();

		Token<String> He = new DefaultToken<String>("He");
		Token<String> witnessed = new DefaultToken<String>("witnessed");
		Token<String> Lennon = new DefaultToken<String>("Lennon");
		Token<String> killed = new DefaultToken<String>("killed");
		Token<String> by = new DefaultToken<String>("by");
		Token<String> Chapman = new DefaultToken<String>("Chapman");
		
		tagger.tag(new DefaultSentence<String>(He, witnessed, Lennon, killed, by, Chapman));

		assertEquals(He.getTag(), "PPS");
		assertEquals(witnessed.getTag(), "VBD");
		assertEquals(Lennon.getTag(), "NP");
		assertEquals(killed.getTag(), "VBN");
		assertEquals(by.getTag(), "BY");
		assertEquals(Chapman.getTag(), "NP");
	}

	private static RuleBasedTagger<String> buildRocheAndSchabes1995SampleTagger() {
		Map<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("killed", "VBN");
		lexicon.put("was", "BEDZ");
		lexicon.put("shot", "VBD");
		lexicon.put("by", "BY");
		lexicon.put("He", "PPS");
		lexicon.put("witnessed", "VBD");
		
		Tagger<String> tagger1 = new LookupTokenTagger<String>(lexicon);
		Tagger<String> tagger2 = new ConstantTokenTagger<String>("NP");
		Tagger<String> baseTagger = new CompositeTagger<String>(tagger1, tagger2);
		
		Rule<String> rule1 = new PREVTAGRule<String>("VBN", "VBD", "NP");
		Rule<String> rule2 = new NEXTTAGRule<String>("VBD", "VBN", "BY");
		
		RuleBasedTagger<String> tagger = new RuleBasedTagger<String>(baseTagger, Arrays.asList(rule1, rule2));
		
		return tagger;
	}
}
