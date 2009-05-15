package br.eti.rslemos.brill;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.rules.PREVTAGRule;
import br.eti.rslemos.brill.rules.WDPREVTAGRule;

public class TaggerExampleBehavior {
	
	@Test
	public void exampleMarkHepple2000() {
		Token to = new DefaultToken("to");
		Token sign = new DefaultToken("sign");
		Token up = new DefaultToken("up");
		
		List<Token> sentence = Arrays.asList(to, sign, up);
		
		Map<String, String> lexicon = new HashMap<String, String>();
		lexicon.put("to", "TO");
		lexicon.put("sign", "NN");
		lexicon.put("up", "RB");
		
		BaseTagger baseTagger = new LookupBaseTagger(lexicon);
		
		Rule rule1 = new PREVTAGRule("NN", "VB", "TO");
		Rule rule2 = new WDPREVTAGRule("RB", "RP", "up", "VB");
		
		Tagger tagger = new Tagger(baseTagger, Arrays.asList(rule1, rule2));
		
		tagger.tagSentence(sentence);
		
		assertEquals(to.getTag(), "TO");
		assertEquals(sign.getTag(), "VB");
		assertEquals(up.getTag(), "RP");
	}
}
