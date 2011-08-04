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
package br.eti.rslemos.brill.events;

import java.util.List;

import br.eti.rslemos.brill.BrillTrainer;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.BrillTrainer.Score;

public abstract privileged aspect BrillTrainerPointcuts {
	
	public pointcut onTraining(BrillTrainer trainer, List<Sentence> baseCorpus, List<Sentence> proofCorpus):
		this(trainer) && 
		execution(public * BrillTrainer+.train(List+, List+)) && args(baseCorpus, proofCorpus) &&
		within(BrillTrainer+);

	public pointcut onPreparing(BrillTrainer trainer):
		this(trainer) &&
		execution(void BrillTrainer+.prepareTrainingCorpus()) && 
		within(BrillTrainer+);
	
	public pointcut onRuleDiscoveryPhase(BrillTrainer trainer):
		this(trainer) &&
		execution(void BrillTrainer+.discoverRules()) && 
		within(BrillTrainer+);
	
	public pointcut onRuleDiscoveryRound(BrillTrainer trainer):
		this(trainer) &&
		execution(Rule+ BrillTrainer+.discoverNextRule()) && 
		within(BrillTrainer+);
	
	public pointcut onPossibleRulesProduction(BrillTrainer trainer):
		this(trainer) &&
		execution(void BrillTrainer+.produceAllPossibleRules()) && 
		within(BrillTrainer+);
	
	public pointcut onBestRuleSelection(BrillTrainer trainer):
		this(trainer) &&
		execution(Score BrillTrainer+.selectBestRule()) && 
		within(BrillTrainer+);
	
}
