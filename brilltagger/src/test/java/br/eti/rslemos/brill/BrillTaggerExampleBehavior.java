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

import org.junit.Before;
import org.junit.Test;

import br.eti.rslemos.brill.rules.NEXTTAGRuleFactory;
import br.eti.rslemos.brill.rules.PREVTAGRuleFactory;
import br.eti.rslemos.brill.rules.WDPREVTAGRuleFactory;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.DefaultToken;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerExampleBehavior {
	
	private Token He;
	private Token witnessed;
	private Token Lennon;
	private Token killed;
	private Token Chapman;
	private Token John;
	private Token was;
	private Token shot;
	private Token by;
	
	@Before
	public void setUp() {
		John = new DefaultToken("John"); John.put(Token.POS, "NP");
		Lennon = new DefaultToken("Lennon"); Lennon.put(Token.POS, "NP");
		was = new DefaultToken("was"); was.put(Token.POS, "BEDZ");
		shot = new DefaultToken("shot"); shot.put(Token.POS, "VBD");
		by = new DefaultToken("by"); by.put(Token.POS, "BY");
		He = new DefaultToken("He"); He.put(Token.POS, "PPS");
		witnessed = new DefaultToken("witnessed"); witnessed.put(Token.POS, "VBD");
		killed = new DefaultToken("killed"); killed.put(Token.POS, "VBN");
		Chapman = new DefaultToken("Chapman"); Chapman.put(Token.POS, "NP");
	}

	@Test
	public void exampleMarkHepple2000() {
		Token to = new DefaultToken("to"); to.put(Token.POS, "TO");
		Token sign = new DefaultToken("sign"); sign.put(Token.POS, "NN");
		Token up = new DefaultToken("up"); up.put(Token.POS, "RB");
		
		Rule rule1 = PREVTAGRuleFactory.INSTANCE.createRule("NN", "VB", "TO");
		Rule rule2 = WDPREVTAGRuleFactory.INSTANCE.createRule("RB", "RP", "VB", "up");
		
		BrillTagger tagger = new BrillTagger(Arrays.asList(rule1, rule2));
		
		tagger.tag(newDefaultSentence(to, sign, up));
		
		assertEquals(to.get(Token.POS), "TO");
		assertEquals(sign.get(Token.POS), "VB");
		assertEquals(up.get(Token.POS), "RP");
	}

	@Test
	public void example1RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		tagger.tag(newDefaultSentence(Chapman, killed, John, Lennon));

		assertEquals(Chapman.get(Token.POS), "NP");
		assertEquals(killed.get(Token.POS), "VBD");
		assertEquals(John.get(Token.POS), "NP");
		assertEquals(Lennon.get(Token.POS), "NP");
	}

	@Test
	public void example2RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();
		
		tagger.tag(newDefaultSentence(John, Lennon, was, shot, by, Chapman));

		assertEquals(John.get(Token.POS), "NP");
		assertEquals(Lennon.get(Token.POS), "NP");
		assertEquals(was.get(Token.POS), "BEDZ");
		assertEquals(shot.get(Token.POS), "VBN");
		assertEquals(by.get(Token.POS), "BY");
		assertEquals(Chapman.get(Token.POS), "NP");
	}

	@Test
	public void example3RocheAndSchabes1995() {
		BrillTagger tagger = buildRocheAndSchabes1995SampleTagger();

		tagger.tag(newDefaultSentence(He, witnessed, Lennon, killed, by, Chapman));

		assertEquals(He.get(Token.POS), "PPS");
		assertEquals(witnessed.get(Token.POS), "VBD");
		assertEquals(Lennon.get(Token.POS), "NP");
		assertEquals(killed.get(Token.POS), "VBN");
		assertEquals(by.get(Token.POS), "BY");
		assertEquals(Chapman.get(Token.POS), "NP");
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
