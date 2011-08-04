/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
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
		Token to = new DefaultToken("to").setFeature(Token.POS, "TO");
		Token sign = new DefaultToken("sign").setFeature(Token.POS, "NN");
		Token up = new DefaultToken("up").setFeature(Token.POS, "RB");
		
		Rule rule1 = PREVTAGRuleFactory.INSTANCE.createRule("NN", "VB", "TO");
		Rule rule2 = WDPREVTAGRuleFactory.INSTANCE.createRule("RB", "RP", "VB", "up");
		
		BrillTagger tagger = new BrillTagger(Arrays.asList(rule1, rule2));
		
		tagger.tag(newDefaultSentence(to, sign, up));
		
		assertEquals(to.getFeature(Token.POS), "TO");
		assertEquals(sign.getFeature(Token.POS), "VB");
		assertEquals(up.getFeature(Token.POS), "RP");
	}

	@Test
	public void example1RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token Chapman = new DefaultToken("Chapman").setFeature(Token.POS, "NP");
		Token killed = new DefaultToken("killed").setFeature(Token.POS, "VBN");;
		Token John = new DefaultToken("John").setFeature(Token.POS, "NP");
		Token Lennon = new DefaultToken("Lennon").setFeature(Token.POS, "NP");
		
		tagger.tag(newDefaultSentence(Chapman, killed, John, Lennon));

		assertEquals(Chapman.getFeature(Token.POS), "NP");
		assertEquals(killed.getFeature(Token.POS), "VBD");
		assertEquals(John.getFeature(Token.POS), "NP");
		assertEquals(Lennon.getFeature(Token.POS), "NP");
	}

	@Test
	public void example2RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		Token John = new DefaultToken("John").setFeature(Token.POS, "NP");
		Token Lennon = new DefaultToken("Lennon").setFeature(Token.POS, "NP");
		Token was = new DefaultToken("was").setFeature(Token.POS, "BEDZ");
		Token shot = new DefaultToken("shot").setFeature(Token.POS, "VBD");
		Token by = new DefaultToken("by").setFeature(Token.POS, "BY");
		Token Chapman = new DefaultToken("Chapman").setFeature(Token.POS, "NP");
		
		tagger.tag(newDefaultSentence(John, Lennon, was, shot, by, Chapman));

		assertEquals(John.getFeature(Token.POS), "NP");
		assertEquals(Lennon.getFeature(Token.POS), "NP");
		assertEquals(was.getFeature(Token.POS), "BEDZ");
		assertEquals(shot.getFeature(Token.POS), "VBN");
		assertEquals(by.getFeature(Token.POS), "BY");
		assertEquals(Chapman.getFeature(Token.POS), "NP");
	}

	@Test
	public void example3RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();

		Token He = new DefaultToken("He").setFeature(Token.POS, "PPS");
		Token witnessed = new DefaultToken("witnessed").setFeature(Token.POS, "VBD");
		Token Lennon = new DefaultToken("Lennon").setFeature(Token.POS, "NP");
		Token killed = new DefaultToken("killed").setFeature(Token.POS, "VBN");
		Token by = new DefaultToken("by").setFeature(Token.POS, "BY");
		Token Chapman = new DefaultToken("Chapman").setFeature(Token.POS, "NP");
		
		tagger.tag(newDefaultSentence(He, witnessed, Lennon, killed, by, Chapman));

		assertEquals(He.getFeature(Token.POS), "PPS");
		assertEquals(witnessed.getFeature(Token.POS), "VBD");
		assertEquals(Lennon.getFeature(Token.POS), "NP");
		assertEquals(killed.getFeature(Token.POS), "VBN");
		assertEquals(by.getFeature(Token.POS), "BY");
		assertEquals(Chapman.getFeature(Token.POS), "NP");
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
