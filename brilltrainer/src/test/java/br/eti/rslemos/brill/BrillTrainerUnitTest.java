/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2013  Rodrigo Lemos
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

import static br.eti.rslemos.brill.CorpusUtils.corpus;
import static br.eti.rslemos.brill.CorpusUtils.retainFeatures;
import static br.eti.rslemos.brill.CorpusUtils.sentence;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import br.eti.rslemos.tagger.Corpus;
import br.eti.rslemos.tagger.DefaultCorpus;


public class BrillTrainerUnitTest {

	@Test(expected = NullPointerException.class)
	public void testNullPatterns() {
		new BrillTrainer().setRulePatterns(null);
	}

	@Test(expected = NullPointerException.class)
	public void testNullInvocation() {
		Collection<RulePattern> patterns = Collections.emptyList();
		BrillTrainer trainer = new BrillTrainer().setRulePatterns(patterns);
		trainer.setCorpora(null, null);
	}
	
	@Test
	public void testEmptyPatternsOverEmptyCorpus() {
		Collection<RulePattern> patterns = Collections.emptyList();
		BrillTrainer trainer = new BrillTrainer().setRulePatterns(patterns);
		Corpus base = new DefaultCorpus();
		Corpus proof = new DefaultCorpus();
		trainer.setCorpora(base, proof);
		List<Rule> rules = trainer.train();
		assertThat(rules, is(not(nullValue(List.class))));
		assertThat(rules.size(), is(equalTo(0)));
	}
	
	@Test
	public void testEmptyPatternsOverText() {
		Collection<RulePattern> patterns = Collections.emptyList();
		BrillTrainer trainer = new BrillTrainer().setRulePatterns(patterns);
		Corpus proof = corpus(sentence("WORD/POS", "The/DET", "quick/ADJ", "brown/ADJ", "fox/NOM", "jumped/VRB", "over/PREP", "the/DET", "lazy/ADJ", "dog/NOM", "./PUN"));
		Corpus base = retainFeatures(proof, "WORD");
		trainer.setCorpora(base, proof);
		List<Rule> rules = trainer.train();
		assertThat(rules, is(not(nullValue(List.class))));
		assertThat(rules.size(), is(equalTo(0)));
	}
	
	@Test
	public void testSinglePatternOverText() {
		RulePattern pattern = new RulePattern();
		pattern.addMatch(0, "WORD");
		pattern.addSet(0, "POS");
		
		Collection<RulePattern> patterns = Collections.singletonList(pattern);
		BrillTrainer trainer = new BrillTrainer().setRulePatterns(patterns);
		Corpus proof = corpus(sentence("WORD/POS", "The/DET", "quick/ADJ", "brown/ADJ", "fox/NOM", "jumped/VRB", "over/PREP", "the/DET", "lazy/ADJ", "dog/NOM", "./PUN"));
		Corpus base = retainFeatures(proof, "WORD");
		trainer.setCorpora(base, proof);
		List<Rule> rules = trainer.train();
		
		assertThat(rules, is(not(nullValue(List.class))));
		assertThat(rules.size(), is(equalTo(10)));
		
		assertThat(rules.get(0).matches.length, is(equalTo(1)));
		assertThat(rules.get(0).matches[0], is(equalTo(Collections.singletonMap("WORD", (Object)"The"))));
		assertThat(rules.get(0).sets, is(equalTo(Collections.singletonMap("POS", (Object)"DET"))));
		
		assertThat(rules.get(1).matches.length, is(equalTo(1)));
		assertThat(rules.get(1).matches[0], is(equalTo(Collections.singletonMap("WORD", (Object)"quick"))));
		assertThat(rules.get(1).sets, is(equalTo(Collections.singletonMap("POS", (Object)"ADJ"))));
		
		assertThat(rules.get(2).matches.length, is(equalTo(1)));
		assertThat(rules.get(2).matches[0], is(equalTo(Collections.singletonMap("WORD", (Object)"brown"))));
		assertThat(rules.get(2).sets, is(equalTo(Collections.singletonMap("POS", (Object)"ADJ"))));
		
		assertThat(rules.get(3).matches.length, is(equalTo(1)));
		assertThat(rules.get(3).matches[0], is(equalTo(Collections.singletonMap("WORD", (Object)"fox"))));
		assertThat(rules.get(3).sets, is(equalTo(Collections.singletonMap("POS", (Object)"NOM"))));
		
		assertThat(rules.get(4).matches.length, is(equalTo(1)));
		assertThat(rules.get(4).matches[0], is(equalTo(Collections.singletonMap("WORD", (Object)"jumped"))));
		assertThat(rules.get(4).sets, is(equalTo(Collections.singletonMap("POS", (Object)"VRB"))));
		
		assertThat(rules.get(5).matches.length, is(equalTo(1)));
		assertThat(rules.get(5).matches[0], is(equalTo(Collections.singletonMap("WORD", (Object)"over"))));
		assertThat(rules.get(5).sets, is(equalTo(Collections.singletonMap("POS", (Object)"PREP"))));
		
		assertThat(rules.get(6).matches.length, is(equalTo(1)));
		assertThat(rules.get(6).matches[0], is(equalTo(Collections.singletonMap("WORD", (Object)"the"))));
		assertThat(rules.get(6).sets, is(equalTo(Collections.singletonMap("POS", (Object)"DET"))));
		
		assertThat(rules.get(7).matches.length, is(equalTo(1)));
		assertThat(rules.get(7).matches[0], is(equalTo(Collections.singletonMap("WORD", (Object)"lazy"))));
		assertThat(rules.get(7).sets, is(equalTo(Collections.singletonMap("POS", (Object)"ADJ"))));
		
		assertThat(rules.get(8).matches.length, is(equalTo(1)));
		assertThat(rules.get(8).matches[0], is(equalTo(Collections.singletonMap("WORD", (Object)"dog"))));
		assertThat(rules.get(8).sets, is(equalTo(Collections.singletonMap("POS", (Object)"NOM"))));
		
		assertThat(rules.get(9).matches.length, is(equalTo(1)));
		assertThat(rules.get(9).matches[0], is(equalTo(Collections.singletonMap("WORD", (Object)"."))));
		assertThat(rules.get(9).sets, is(equalTo(Collections.singletonMap("POS", (Object)"PUN"))));
	}
}
