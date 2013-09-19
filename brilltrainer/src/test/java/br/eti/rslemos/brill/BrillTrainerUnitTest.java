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
		new BrillTrainer(null);
	}

	@Test(expected = NullPointerException.class)
	public void testNullInvocation() {
		Collection<RulePattern> patterns = Collections.emptyList();
		BrillTrainer trainer = new BrillTrainer(patterns);
		trainer.train(null, null);
	}
	
	@Test
	public void testEmptyPatternsOverEmptyCorpus() {
		Collection<RulePattern> patterns = Collections.emptyList();
		BrillTrainer trainer = new BrillTrainer(patterns);
		Corpus base = new DefaultCorpus();
		Corpus proof = new DefaultCorpus();
		List<Rule> rules = trainer.train(base, proof);
		assertThat(rules, is(not(nullValue(List.class))));
		assertThat(rules.size(), is(equalTo(0)));
	}
	
	@Test
	public void testEmptyPatternsOverText() {
		Collection<RulePattern> patterns = Collections.emptyList();
		BrillTrainer trainer = new BrillTrainer(patterns);
		Corpus proof = corpus(sentence("WORD/POS", "The/DET", "quick/ADJ", "brown/ADJ", "fox/NOM", "jumped/VRB", "over/PREP", "the/DET", "lazy/ADJ", "dog/NOM", "./PUN"));
		Corpus base = retainFeatures(proof, "WORD");
		List<Rule> rules = trainer.train(base, proof);
		assertThat(rules, is(not(nullValue(List.class))));
		assertThat(rules.size(), is(equalTo(0)));
	}
}
